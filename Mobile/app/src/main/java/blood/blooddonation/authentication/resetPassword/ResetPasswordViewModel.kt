package blood.blooddonation.authentication.resetPassword

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
import blood.blooddonation.service.AuthService
import kotlinx.coroutines.launch

data class ResetPasswordUiState(
    val resetComplete: Boolean = false,
    val resetError: Throwable? = null,
    val resetInProgress: Boolean = false
)

class ResetPasswordViewModel(
    private val authService: AuthService,
) : ViewModel() {

    var uiState: ResetPasswordUiState by mutableStateOf(ResetPasswordUiState())

    fun resetPassword(email: String, cnp: String, password: String, confirmPassword: String) {
        viewModelScope.launch {

            if (email.isBlank()) {
                uiState = uiState.copy(
                    resetComplete = false,
                    resetError = IllegalArgumentException("Email-ul nu poate fi gol")
                )
                return@launch
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                uiState = uiState.copy(
                    resetComplete = false,
                    resetError = IllegalArgumentException("Email-ul are un format invalid")
                )
                return@launch
            }

            if (password.isBlank() || confirmPassword.isBlank()) {
                uiState = uiState.copy(
                    resetComplete = false,
                    resetError = IllegalArgumentException("Parola nu poate fi goala")
                )
                return@launch
            }

            if (password != confirmPassword) {
                uiState = uiState.copy(
                    resetComplete = false,
                    resetError = IllegalArgumentException("Parolele nu sunt identice")
                )
                return@launch
            }

            if (password.length < 6) {
                uiState = uiState.copy(
                    resetComplete = false,
                    resetError = IllegalArgumentException("Parola este prea scurta. Sunt necesare minim 6 caractere")
                )
                return@launch
            }

            if (cnp.length != 13) {
                uiState = uiState.copy(
                    resetComplete = false,
                    resetError = IllegalArgumentException("CNP-ul trebuie sa aibe exact 13 caractere")
                )
                return@launch
            }

            uiState = uiState.copy(resetError = null, resetInProgress = true)

            val encryptedCNP = Utils().hashString(cnp)
            val encryptedPassword = Utils().hashString(password)
            val id = Utils().hashString(email)

            val result = authService.resetPassword(id, email, encryptedCNP, encryptedPassword)
            if (result.isSuccess) {
                uiState = uiState.copy(
                    resetError = null,
                    resetComplete = true
                )
            } else {
                uiState = uiState.copy(
                    resetError = result.exceptionOrNull(),
                    resetInProgress = false
                )
            }
        }
    }

    fun clearAuthenticationError() {
        uiState = uiState.copy(resetError = null)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                ResetPasswordViewModel(app.container.authService)
            }
        }
    }
}