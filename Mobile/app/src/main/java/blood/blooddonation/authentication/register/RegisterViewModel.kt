package blood.blooddonation.authentication.register

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

data class RegisterUiState(
    val isAuthenticating: Boolean = false,
    val authenticationError: Throwable? = null,
    val authenticationCompleted: Boolean = false,
    val token: String = "",
)

class RegisterViewModel(
    private val authService: AuthService,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    var uiState: RegisterUiState by mutableStateOf(RegisterUiState())

    fun register(
        email: String,
        cnp: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {

            if (email.isBlank()) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = IllegalArgumentException("Email-ul nu poate fi gol")
                )
                return@launch
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = IllegalArgumentException("Email-ul are un format invalid")
                )
                return@launch
            }

            if (password.isBlank() || confirmPassword.isBlank()) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = IllegalArgumentException("Parola nu poate fi goala")
                )
                return@launch
            }

            if (password != confirmPassword) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = IllegalArgumentException("Parolele nu sunt identice")
                )
                return@launch
            }

            if (password.length < 6) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = IllegalArgumentException("Parola este prea scurta. Sunt necesare minim 6 caractere")
                )
                return@launch
            }

            if (cnp.length != 13) {
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationError = IllegalArgumentException("CNP-ul trebuie sa aibe exact 13 caractere")
                )
                return@launch
            }

            uiState = uiState.copy(isAuthenticating = true, authenticationError = null)

            val encryptedCNP = Utils().hashString(cnp)
            val encryptedPassword = Utils().hashString(password)
            val id = Utils().hashString(email)

            val result = authService.register(id, email, encryptedCNP, encryptedPassword)
            if(result.isSuccess){
                userPreferencesRepository.saveToken(
                    UserPreferences(
                        id = id,
                        token = result.getOrNull()?.token ?: "",
                        finish = false
                    )
                )
                uiState = uiState.copy(
                    isAuthenticating = false,
                    authenticationCompleted = true
                )
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
                RegisterViewModel(
                    app.container.authService,
                    app.container.userPreferencesRepository
                )
            }
        }
    }
}