package com.example.conductormvvm.repository

import com.example.conductormvvm.data.domain.SocketMessage
import com.example.conductormvvm.util.utils.Event
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class WebSocketRepository(globalScope: CoroutineScope) {

    // todo move to websocketremotedatasource

    /**
     * Shared flow which starts emitting when first subscribed, and only if subscribed.
     * Helps save resources in backstack controllers when combined with LiveData.
     * Sharing in external scope also does not cause to start from 0 on re-subscription
     * */
    val socketMessages: SharedFlow<Event<SocketMessage>> = flow {
        yield()
        repeat(10000) { i ->
            val socketMessage = SocketMessage(content = i.toString())
            emit(Event(socketMessage))
            delay(1000)
        }
    }.shareIn(globalScope, SharingStarted.WhileSubscribed())

    // todo test real websockets https://ssaurel.medium.com/learn-to-use-websockets-on-android-with-okhttp-ba5f00aea988
}