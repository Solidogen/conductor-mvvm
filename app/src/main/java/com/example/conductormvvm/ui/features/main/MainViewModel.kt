package com.example.conductormvvm.ui.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conductormvvm.repository.AppRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber

class MainViewModel(private val appRepository: AppRepository) : ViewModel() {

    init {
        subscribe()
        appRepository.startEmittingGlobalEvents()
    }

    private fun subscribe() {
        appRepository.globalEvents
            .onEach {
                Timber.d("Receiving global event in ${this::class.java.simpleName}: $it")
            }.launchIn(viewModelScope)
    }
}