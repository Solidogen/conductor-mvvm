package com.example.conductormvvm.util.utils.observable

import com.example.conductormvvm.util.utils.ErrorType
import com.example.conductormvvm.util.utils.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ErrorManager {

    private val _errors: MutableSharedFlow<Event<ErrorType>> = MutableSharedFlow()

    val errors: SharedFlow<Event<ErrorType>>
        get() = _errors

    suspend fun handleApiError(errorType: ErrorType) {
        _errors.emit(Event(errorType))
    }
}