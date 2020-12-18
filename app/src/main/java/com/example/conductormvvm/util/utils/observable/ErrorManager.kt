package com.example.conductormvvm.util.utils.observable

import com.example.conductormvvm.util.utils.ErrorType
import com.example.conductormvvm.util.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ErrorManager(private val globalScope: CoroutineScope) {

    private val _errors: MutableSharedFlow<Event<ErrorType>> = MutableSharedFlow()

    val errors: SharedFlow<Event<ErrorType>>
        get() = _errors

    fun handleApiError(errorType: ErrorType) {
        globalScope.launch {
            _errors.emit(Event(errorType))
        }
    }
}