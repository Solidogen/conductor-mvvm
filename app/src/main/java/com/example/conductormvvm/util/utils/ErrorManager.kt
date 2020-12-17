package com.example.conductormvvm.util.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ErrorManager {

    private val _errors: MutableSharedFlow<ErrorType> = MutableSharedFlow()

    val errors: SharedFlow<ErrorType>
        get() = _errors

    suspend fun handleApiError(errorType: ErrorType) {
        _errors.emit(errorType)
    }
}