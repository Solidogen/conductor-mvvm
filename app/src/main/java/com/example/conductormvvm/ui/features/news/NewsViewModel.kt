package com.example.conductormvvm.ui.features.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conductormvvm.data.domain.NewsData
import com.example.conductormvvm.repository.HomeRepository
import com.example.conductormvvm.util.utils.ErrorManager
import kotlinx.coroutines.launch

class NewsViewModel(
    private val homeRepository: HomeRepository,
    private val errorManager: ErrorManager
) : ViewModel() {

    private val _newsData = MutableLiveData<NewsData>()
    val newsData: LiveData<NewsData> get() = _newsData

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            homeRepository.getNewsData()
                .onSuccess { _newsData.value = it }
                .onError { errorManager.handleApiError(it) }
        }
    }
}