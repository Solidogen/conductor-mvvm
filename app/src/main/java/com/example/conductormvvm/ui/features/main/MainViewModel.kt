package com.example.conductormvvm.ui.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conductormvvm.repository.AppRepository
import com.example.conductormvvm.repository.GlobalEvent
import com.example.conductormvvm.util.extensions.emitEvent
import com.example.conductormvvm.util.utils.Event
import kotlinx.coroutines.flow.*
import timber.log.Timber

class MainViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _globalEvent = MutableLiveData<Event<GlobalEvent>>()
    val globalEvent: LiveData<Event<GlobalEvent>> get() = _globalEvent

    init {
        observeGlobalEvents()
        appRepository.startEmittingGlobalEvents()
    }

    private fun observeGlobalEvents() {
        appRepository.globalEvents
            .onEach {
                Timber.d("Main: Receiving global event: $it")
                _globalEvent.emitEvent(it)
            }.launchIn(viewModelScope)
    }
}