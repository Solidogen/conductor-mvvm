package com.example.conductormvvm.repository

import com.example.conductormvvm.data.domain.SocketMessage
import com.example.conductormvvm.util.utils.Event
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class WebSocketRepository(globalScope: CoroutineScope) {
    /**
     * Shared flow which starts emitting when first subscribed, and only if subscribed.
     * Helps save resources in backstack controllers when combined with LiveData
     * */
    val socketMessages: SharedFlow<Event<SocketMessage>> = flow {
        yield()
        repeat(10000) { i ->
            val socketMessage = SocketMessage(content = i.toString())
            emit(Event(socketMessage))
            delay(1000)
        }
    }.shareIn(globalScope, SharingStarted.WhileSubscribed())
}