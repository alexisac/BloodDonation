package blood.blooddonation.chatLive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import blood.blooddonation.service.ChatLiveService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatBotLiveUiState(
    val error: Throwable? = null,
    val inProgress: Boolean = false,
    val complete: Boolean = false,
    val questions: List<String> = emptyList(),
    val answers: List<String> = emptyList()
)

class ChatLiveViewModel(
    private val chatLiveService: ChatLiveService
): ViewModel() {
    private val _uiState = MutableStateFlow(ChatBotLiveUiState())
    val uiState = _uiState.asStateFlow()

    init {
        clearChatHistory()
    }

    fun getChatBotAnswer(question: String) {
        viewModelScope.launch {
            val updatedQuestions = _uiState.value.questions.toMutableList().apply { add(question) }
            _uiState.value = _uiState.value.copy(
                error = null,
                inProgress = true,
                questions = updatedQuestions
            )

            val result = chatLiveService.getChatBotAnswer(question)
            result.onSuccess {answer ->
                val updatedAnswers = _uiState.value.answers.toMutableList().apply { add(answer) }
                _uiState.value = uiState.value.copy(
                    error = null,
                    complete = true,
                    inProgress = false,
                    answers = updatedAnswers
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    error = result.exceptionOrNull(),
                    inProgress = false
                )
            }
        }
    }

    private fun clearChatHistory() {
        _uiState.value = _uiState.value.copy(questions = emptyList(), answers = emptyList())
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                ChatLiveViewModel(app.container.chatLiveService)
            }
        }
    }
}