package com.example.conductormvvm.repository

import com.example.conductormvvm.data.domain.FakeSocketMessage
import com.example.conductormvvm.datasource.IWebSocketRemoteDataSource
import com.example.conductormvvm.util.utils.Event
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class WebSocketRepository(
    webSocketRemoteDataSource: IWebSocketRemoteDataSource,
    globalScope: CoroutineScope
) {

    /**
     * Shared flow which starts emitting when first subscribed, and only if subscribed.
     * Helps save resources in backstack controllers when combined with LiveData.
     * Makes cold flow hot.
     * Sharing in external scope also does not cause to restart it on re-subscription, as it's hot now.
     * */
    val fakeSocketMessages: SharedFlow<Event<FakeSocketMessage>> =
        webSocketRemoteDataSource.fakeSocketMessages.shareIn(
            globalScope,
            SharingStarted.WhileSubscribed()
        )

    // todo test real websockets https://ssaurel.medium.com/learn-to-use-websockets-on-android-with-okhttp-ba5f00aea988
}