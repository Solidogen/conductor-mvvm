package com.example.conductormvvm.ui.features.home

import androidx.lifecycle.*
import com.example.conductormvvm.data.HomeData
import com.example.conductormvvm.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(val homeRepository: HomeRepository) : ViewModel() {

    private val _homeData = MutableLiveData<HomeData>()
    val homeData: LiveData<HomeData> get() = _homeData

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val homeData = homeRepository.getHomeData()
            _homeData.value = homeData
        }
    }
}