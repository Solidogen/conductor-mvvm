package com.example.conductormvvm.ui.features.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conductormvvm.data.NewsData
import com.example.conductormvvm.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _newsData = MutableLiveData<NewsData>()
    val newsData: LiveData<NewsData> get() = _newsData

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val newsData = newsRepository.getNewsData()
            _newsData.value = newsData
        }
    }
}