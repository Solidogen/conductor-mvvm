package com.example.conductormvvm.util.utils.observable

import com.example.conductormvvm.data.response.RealSocketMessageDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import okhttp3.*
import okio.ByteString
import timber.log.Timber
import kotlin.random.Random

class WebSocketManager(
    private val okHttpClient: OkHttpClient,
    private val globalScope: CoroutineScope
) {
    private val webSocket: WebSocket
    private val _socketMessages: MutableSharedFlow<RealSocketMessageDto> = MutableSharedFlow()

    val socketMessages: SharedFlow<RealSocketMessageDto>
        get() = _socketMessages

    init {
        val request = Request.Builder().url("ws://echo.websocket.org").build()
        webSocket =
            okHttpClient.newWebSocket(request = request, listener = createWebSocketListener())
        globalScope.launch {
            yield()
            repeat(10000) {
                webSocket.send("todojson")
                delay(1000)
            }
        }
    }

    private fun createWebSocketListener(): WebSocketListener = object : WebSocketListener() {
        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Timber.d("WebSocket: onClosed, code: $code, reason: $reason")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Timber.d("WebSocket: onClosing, code: $code, reason: $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Timber.e(t, "WebSocket: onFailure, response: $response")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Timber.d("WebSocket: onMessage, text: $text")
            globalScope.launch {
                _socketMessages.emit(
                    RealSocketMessageDto(
                        username = "user$text",
                        isRich = Random.nextBoolean()
                    )
                )
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Timber.d("WebSocket: onMessage, bytes: $bytes")
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Timber.d("WebSocket: onOpen, response: $response")
        }
    }
}