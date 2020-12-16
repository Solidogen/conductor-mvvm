package com.example.conductormvvm.util.utils

class ApiCallWrapper

sealed class ApiCallResult<T> {
    data class Success<T>(val data: T) : ApiCallResult<T>()
    data class Error<T>(val error: ApiCallError) : ApiCallResult<T>()
}

sealed class ApiCallError {
    data class ServerError(override val message: String) : ApiCallError()
    object JsonParsingError : ApiCallError() {
        override val message = JSON_PARSING_ERROR_MESSAGE
    }

    abstract val message: String

    companion object {
        private const val JSON_PARSING_ERROR_MESSAGE =
            "Failed to parse server response. Make sure you have latest app version"
    }
}

