package com.example.conductormvvm.repository

import com.example.conductormvvm.data.GlobalEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// todo inject dispatcher, inject applicationScope to share flows
class GlobalEventRepository() {

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

    val pureFlowGlobalEvents: Flow<GlobalEvent> = flow {
        yield()
        repeat(10000) { i ->
            val globalEvent = GlobalEvent(content = i.toString())
            emit(globalEvent)
            delay(1000)
        }
    }//.shareIn() todo try sharing this flow, need scope
}