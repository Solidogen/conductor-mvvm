package com.example.conductormvvm.repository

import com.example.conductormvvm.data.domain.AddData
import kotlinx.coroutines.delay

class AddRepository {

    suspend fun getAddData(): AddData {
        delay(2000)
        return AddData("add-data")
    }
}