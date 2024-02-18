package com.ppidev.smartcube.domain.ext_service

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class WebSocketListenerService @Inject constructor() : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        webSocket.send("Android Device Connected")
        Log.d("WEBSOCKET","connected websocket")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d("WEBSOCKET", "Payload : $text")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d("WEBSOCKET","connected closed")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d("WEBSOCKET", "failure connect")
        super.onFailure(webSocket, t, response)
    }
}