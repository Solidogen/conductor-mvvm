package com.example.conductormvvm.repository

import com.example.conductormvvm.data.HomeData
import kotlinx.coroutines.delay

class HomeRepository {

    // this should return result

    suspend fun getHomeData(): HomeData {
        delay(2000)
        return HomeData("home-data")
    }
}





