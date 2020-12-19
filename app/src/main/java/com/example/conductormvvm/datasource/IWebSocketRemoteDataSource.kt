package com.example.conductormvvm.datasource

import com.example.conductormvvm.data.domain.FakeSocketMessage
import com.example.conductormvvm.data.response.RealSocketMessageDto
import com.example.conductormvvm.util.utils.observable.WebSocketManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield

interface IWebSocketRemoteDataSource {
    val realSocketMessages: Flow<RealSocketMessageDto>
    val fakeSocketMessages: Flow<FakeSocketMessage>
}

class WebSocketRemoteDataSource(private val webSocketManager: WebSocketManager) : IWebSocketRemoteDataSource {

    override val realSocketMessages: Flow<RealSocketMessageDto>
        get() = webSocketManager.socketMessages

    override val fakeSocketMessages: Flow<FakeSocketMessage> = flow {
        yield()
        repeat(10000) { i ->
            val socketMessage = FakeSocketMessage(content = i.toString())
            emit(socketMessage)
            delay(1000)
        }
    }
}