package com.example.conductormvvm.repository

import com.example.conductormvvm.data.HomeData
import kotlinx.coroutines.delay

class HomeRepository {

    suspend fun getHomeData(): HomeData {
        delay(2000)
        return HomeData("home-data")
    }
}





