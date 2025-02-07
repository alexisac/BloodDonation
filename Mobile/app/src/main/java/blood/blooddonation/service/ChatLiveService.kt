package blood.blooddonation.service

import blood.blooddonation.repository.ChatLiveRepository

class ChatLiveService(
    private val chatLiveRepository: ChatLiveRepository
) {
    suspend fun getChatBotAnswer(question: String): Result<String> {
        return chatLiveRepository.getChatBotAnswer(question)
    }
}