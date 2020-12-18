package com.example.conductormvvm.datasource

import com.example.conductormvvm.data.domain.FakeSocketMessage
import com.example.conductormvvm.util.utils.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield

interface IWebSocketRemoteDataSource {
    val fakeSocketMessages: Flow<Event<FakeSocketMessage>>
}

class WebSocketRemoteDataSource : IWebSocketRemoteDataSource {

    override val fakeSocketMessages: Flow<Event<FakeSocketMessage>> = flow {
        yield()
        repeat(10000) { i ->
            val socketMessage = FakeSocketMessage(content = i.toString())
            emit(Event(socketMessage))
            delay(1000)
        }
    }
}