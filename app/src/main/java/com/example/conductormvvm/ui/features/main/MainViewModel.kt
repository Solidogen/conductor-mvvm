package com.example.conductormvvm.ui.features.main

import androidx.lifecycle.*
import com.example.conductormvvm.data.domain.GlobalEvent
import com.example.conductormvvm.repository.GlobalEventRepository
import com.example.conductormvvm.util.utils.ErrorManager
import com.example.conductormvvm.util.utils.ErrorType
import com.example.conductormvvm.util.utils.Event
import kotlinx.coroutines.flow.*

class MainViewModel(
    private val globalEventRepository: GlobalEventRepository,
    private val errorManager: ErrorManager
) : ViewModel() {

    val globalEvents: LiveData<Event<GlobalEvent>>
        get() = globalEventRepository.globalEvents.map { Event(it) }.asLiveData()

    val errors: LiveData<Event<ErrorType>>
        get() = errorManager.errors.map { Event(it) }.asLiveData()

    init {
        globalEventRepository.startEmittingGlobalEvents()
    }
}