package com.example.conductormvvm.repository

import com.example.conductormvvm.data.SettingsData
import kotlinx.coroutines.delay

class SettingsRepository {

    suspend fun getSettingsData(): SettingsData {
        delay(2000)
        return SettingsData("settings-data")
    }
}