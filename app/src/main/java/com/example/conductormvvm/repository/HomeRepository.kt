package com.example.conductormvvm.repository

import com.example.conductormvvm.data.HomeData
import com.example.conductormvvm.util.utils.ApiCallWrapper
import kotlinx.coroutines.delay

class HomeRepository(private val apiCallWrapper: ApiCallWrapper) {

    // this should return result

    suspend fun getHomeData(): HomeData {
        delay(2000)
        return HomeData("home-data")
    }
}