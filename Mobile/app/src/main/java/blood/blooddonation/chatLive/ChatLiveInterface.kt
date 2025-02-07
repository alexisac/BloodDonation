package blood.blooddonation.chatLive

import blood.blooddonation.model.ChatMessage
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatLiveInterface {
    @Headers("Content-Type: application/json")
    @POST("/chatBot/getAnswer")
    suspend fun getChatBotAnswer(
        @Header("Authorization") authorization: String,
        @Body question: String
    ): ChatMessage
}