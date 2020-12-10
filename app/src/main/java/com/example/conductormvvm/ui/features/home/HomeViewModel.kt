package com.example.conductormvvm.ui.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conductormvvm.repository.AppRepository
import com.example.conductormvvm.repository.HomeData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(private val appRepository: AppRepository) : ViewModel() {

    // https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#livedata

    private val _homeData = MutableLiveData<HomeData>()
    val homeData: LiveData<HomeData> get() = _homeData

    init {
        observeGlobalEvents()
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val homeData = appRepository.getHome()
            _homeData.value = homeData
        }
    }

    private fun observeGlobalEvents() {
        appRepository.globalEvents
            .onEach {
                Timber.d("  Home: Receiving global event: $it")
            }.launchIn(viewModelScope)
    }
}