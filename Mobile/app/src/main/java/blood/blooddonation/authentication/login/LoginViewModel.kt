package blood.blooddonation.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import blood.blooddonation.authentication.Utils
import blood.blooddonation.preferences.UserPreferences
import blood.blooddonation.preferences.UserPreferencesRepository
import blood.blooddonation.service.AuthService
import kotlinx.coroutines.launch
data class LoginUiState(
    val isAuthenticating: Boolean = false,
    val authenticationError: Throwable? = null,
    val authenticationCompleted: Boolean = false,
    val token: String = ""
)
class LoginViewModel(
    private val authService: AuthService,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var uiState: LoginUiState by mutableStateOf(LoginUiState())
    fun login(email: String, password: String) {
        viewModelScope.launch {

            if (email.isBlank()) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = IllegalArgumentException("Email-ul nu poate fi gol")
                )
                return@launch
            }

            if (password.isBlank()) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = IllegalArgumentException("Parola nu poate fi goala")
                )
                return@launch
            }

            uiState = uiState.copy(isAuthenticating = true, authenticationError = null)

            val encryptedPassword = Utils().hashString(password)
            val id = Utils().hashString(email)

            val result = authService.login(email, encryptedPassword)
            if (result.isSuccess) {
                userPreferencesRepository.saveToken(
                    UserPreferences(
                        id = id,
                        token = result.getOrNull()?.token ?: "",
                        finish = true
                    )
                )
                uiState = uiState.copy(isAuthenticating = false, authenticationCompleted = true)
            } else {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = result.exceptionOrNull()
                )
            }
        }
    }
    fun clearAuthenticationError() {
        uiState = uiState.copy(authenticationError = null)
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                LoginViewModel(
                    app.container.authService,
                    app.container.userPreferencesRepository
                )
            }
        }
    }
}