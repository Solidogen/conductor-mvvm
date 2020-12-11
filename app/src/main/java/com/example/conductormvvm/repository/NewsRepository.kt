package com.example.conductormvvm.repository

import com.example.conductormvvm.data.NewsData
import kotlinx.coroutines.delay

class NewsRepository {

    suspend fun getNewsData(): NewsData {
        delay(2000)
        return NewsData("news-data")
    }
}