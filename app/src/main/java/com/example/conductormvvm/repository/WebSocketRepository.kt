package com.example.conductormvvm.repository

import com.example.conductormvvm.data.domain.FakeSocketMessage
import com.example.conductormvvm.data.domain.RealSocketMessage
import com.example.conductormvvm.data.mapper.toDomainModel
import com.example.conductormvvm.datasource.IWebSocketRemoteDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class WebSocketRepository(
    private val webSocketRemoteDataSource: IWebSocketRemoteDataSource,
    globalScope: CoroutineScope
) {

    /**
     * Shared flow which starts emitting when first subscribed, and only if subscribed.
     * Helps save resources in backstack controllers when combined with LiveData.
     * Makes cold flow hot.
     * Sharing in external scope also does not cause to restart it on re-subscription, as it's hot now.
     * */
    val fakeSocketMessages: SharedFlow<FakeSocketMessage> =
        webSocketRemoteDataSource.fakeSocketMessages
            .shareIn(globalScope, SharingStarted.WhileSubscribed())

    val realSocketMessages: SharedFlow<RealSocketMessage> =
        webSocketRemoteDataSource.realSocketMessages.map { it.toDomainModel() }
            .shareIn(globalScope, SharingStarted.WhileSubscribed())
}