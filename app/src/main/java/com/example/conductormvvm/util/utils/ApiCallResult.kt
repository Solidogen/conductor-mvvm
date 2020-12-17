package com.example.conductormvvm.util.utils

import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

sealed class ApiCallResult<T> {
    data class Success<T>(val data: T) : ApiCallResult<T>()
    data class Error<T>(val errorType: ErrorType) : ApiCallResult<T>()

    suspend fun onSuccess(block: suspend (T) -> Unit): ApiCallResult<T> = also {
        if (it is Success<T>) {
            block.invoke(it.data)
        }
    }

    suspend fun onError(block: suspend (ErrorType) -> Unit): ApiCallResult<T> = also {
        if (it is Error<T>) {
            block.invoke(it.errorType)
        }
    }

    companion object {
        fun <T> errorFromException(e: Throwable): Error<T> =
            when (e) {
                is RemoteException -> Error(
                    errorType = ErrorType.RemoteError(
                        message = e.message.orEmpty(),
                        statusCode = e.statusCode
                    )
                )
                is JsonDataException -> Error(errorType = ErrorType.JsonParsingError)
                else -> Error(errorType = ErrorType.UnexpectedError)
            }
    }
}

sealed class ErrorType {
    data class RemoteError(
        override val message: String,
        val statusCode: StatusCode
    ) : ErrorType()

    object JsonParsingError : ErrorType() {
        override val message = JSON_PARSING_ERROR_MESSAGE
    }

    object UnexpectedError : ErrorType() {
        override val message = UNEXPECTED_ERROR_MESSAGE
    }

    abstract val message: String

    companion object {
        private const val JSON_PARSING_ERROR_MESSAGE =
            "Failed to parse server response. Make sure you have latest app version"
        private const val UNEXPECTED_ERROR_MESSAGE =
            "Unexpected error"
    }
}

suspend inline fun <T, R> runCatchingAsync(
    noinline call: suspend () -> T,
    crossinline mapper: (T) -> R,
    crossinline doBeforeMapping: (T) -> Unit = {},
    crossinline doAfterMapping: (R) -> Unit = {},
    crossinline doOnError: () -> Unit = {},
    appDispatchers: IAppDispatchers
): ApiCallResult<R> =
    withContext(appDispatchers.io) {
        try {
            val data = call.invoke().also(doBeforeMapping)
            val mappedData = data.let(mapper).also(doAfterMapping)
            ApiCallResult.Success(mappedData)
        } catch (cancellationException: CancellationException) {
            throw cancellationException // handled by coroutine scope
        } catch (e: Throwable) {
            val methodName = call::class.java.name
            Timber.e(e, "Caught error during call of $methodName")
            doOnError.invoke()
            ApiCallResult.errorFromException<R>(e)
        }
    }

class RemoteException(message: String, val statusCode: StatusCode, cause: Throwable? = null) :
    IOException(message, cause)

fun <T> Response<T>.bodyOrThrow(): T = takeIf { it.isSuccessful }?.body() ?: throw RemoteException(
    message = errorBody()?.string().orEmpty(),
    statusCode = StatusCode.fromRawValue(rawValue = code())
)

enum class StatusCode(val rawValue: Int) {
    BadRequest(400),
    Unauthorized(401),
    Forbidden(403),
    NotFound(404),
    Conflict(409),
    Gone(410),
    InternalServerError(500),
    NotImplemented(501),
    BadGateway(502),
    ServiceUnavailable(503),
    GatewayTimeout(504),
    Undefined(-1);

    companion object {
        fun fromRawValue(rawValue: Int): StatusCode =
            values().find { it.rawValue == rawValue } ?: Undefined
    }
}