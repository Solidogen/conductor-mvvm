package com.example.conductormvvm.repository

import com.example.conductormvvm.data.domain.GlobalEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class WebSocketRepository(globalScope: CoroutineScope) {
    /**
     * Shared flow which starts emitting when first subscribed, and only if subscribed.
     * Helps save resources in backstack controllers when combined with LiveData
     * */
    val globalEvents: SharedFlow<GlobalEvent> = flow {
        yield()
        repeat(10000) { i ->
            val globalEvent = GlobalEvent(content = i.toString())
            emit(globalEvent)
            delay(1000)
        }
    }.shareIn(globalScope, SharingStarted.WhileSubscribed())
}