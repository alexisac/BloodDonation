package blood.blooddonation.item

import blood.blooddonation.utils.Api
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class ItemWsClient(private val okHttpClient: OkHttpClient) {

    lateinit var webSocket: WebSocket

    suspend fun openSocket(
        onEvent: (itemEvent: ItemEvent?) -> Unit,
        onClosed: () -> Unit,
        onFailure: () -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val request = Request.Builder().url(Api.webSoketUrl).build()
            webSocket = okHttpClient.newWebSocket(
                request,
                ItemWebSocketListener(onEvent = onEvent, onClosed = onClosed, onFailure = onFailure)
            )
            okHttpClient.dispatcher.executorService.shutdown()
        }
    }

    fun closeSocket() {
        webSocket.close(1000, "");
    }

    inner class ItemWebSocketListener(
        private val onEvent: (itemEvent: ItemEvent?) -> Unit,
        private val onClosed: () -> Unit,
        private val onFailure: () -> Unit
    ) : WebSocketListener() {
        private val moshi = Moshi.Builder()
            .build()
        private val itemEventJsonAdapter: JsonAdapter<ItemEvent> =
            moshi.adapter(ItemEvent::class.java)

        override fun onOpen(webSocket: WebSocket, response: Response) {
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            val itemEvent = itemEventJsonAdapter.fromJson(text)
            onEvent(itemEvent)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {}

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            onClosed()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            onFailure()
        }
    }

    fun authorize(token: String) {
        val auth = """
            {
              "type":"authorization",
              "payload":{
                "token": "$token"
              }
            }
        """.trimIndent()
        webSocket.send(auth)
    }
}