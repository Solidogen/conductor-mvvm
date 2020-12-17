package com.example.conductormvvm.ui.features.home

import androidx.lifecycle.*
import com.example.conductormvvm.data.domain.HomeData
import com.example.conductormvvm.repository.HomeRepository
import com.example.conductormvvm.util.utils.ErrorManager
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val errorManager: ErrorManager
) : ViewModel() {

    private val _homeData = MutableLiveData<HomeData>()
    val homeData: LiveData<HomeData> get() = _homeData

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            homeRepository.getHomeData()
                .onSuccess { _homeData.value = it }
                .onError { errorManager.handleApiError(it) }
        }
    }
}