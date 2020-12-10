package com.example.conductormvvm.repository

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AppRepository {

    private val _globalEvents: MutableSharedFlow<GlobalEvent> = MutableSharedFlow()

    val globalEvents: SharedFlow<GlobalEvent>
        get() = _globalEvents

    fun startEmittingGlobalEvents() {
        GlobalScope.launch {
            yield()
            repeat(10000) { i ->
                val globalEvent = GlobalEvent(content = i.toString())
                _globalEvents.emit(globalEvent)
                delay(1000)
            }
        }
    }

    suspend fun getHomeData(): HomeData {
        delay(2000)
        return HomeData("home-data")
    }

    suspend fun getNewsData(): NewsData {
        delay(2000)
        return NewsData("news-data")
    }

    suspend fun getAddData(): AddData {
        delay(2000)
        return AddData("add-data")
    }

    suspend fun getSettingsData(): SettingsData {
        delay(2000)
        return SettingsData("settings-data")
    }

    suspend fun getHome(): HomeData {
        delay(2000)
        return HomeData("home-data")
    }
}

data class HomeData(val content: String)
data class NewsData(val content: String)
data class AddData(val content: String)
data class SettingsData(val content: String)
data class GlobalEvent(val content: String)