package com.example.conductormvvm.ui.features.main

import androidx.lifecycle.*
import com.example.conductormvvm.data.GlobalEvent
import com.example.conductormvvm.repository.GlobalEventRepository
import com.example.conductormvvm.util.utils.Event
import kotlinx.coroutines.flow.*

class MainViewModel(private val globalEventRepository: GlobalEventRepository) : ViewModel() {

    val globalEvents: LiveData<Event<GlobalEvent>>
        get() = globalEventRepository.globalEvents.map { Event(it) }.asLiveData()

    init {
        globalEventRepository.startEmittingGlobalEvents()
    }
}