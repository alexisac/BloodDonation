package blood.blooddonation.GeneralInformation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import blood.blooddonation.model.enums.BloodType
import blood.blooddonation.model.enums.Sex
import blood.blooddonation.preferences.UserPreferencesRepository
import blood.blooddonation.service.GeneralInfoService
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

data class GeneralInformaionUiState(
    val sendInfo: Boolean = false,
    val sendError: Throwable? = null,
    val sendCompleted: Boolean = false
)

class GeneralInformationViewModel(
    private val generalInfoService: GeneralInfoService,
    private val userPreferencesRepository: UserPreferencesRepository
):ViewModel() {

    var uiState: GeneralInformaionUiState by mutableStateOf(GeneralInformaionUiState())

    fun saveGeneralInfo(
        firstName: String,
        lastName: String,
        birthDate: Date,
        sex: Sex,
        bloodType: BloodType
    ) {
        viewModelScope.launch {

            if (firstName.isBlank()) {
                uiState = uiState.copy(
                    sendInfo = false,
                    sendError = IllegalArgumentException("Numele nu poate fi gol")
                )
                return@launch
            }

            if (lastName.isBlank()) {
                uiState = uiState.copy(
                    sendInfo = false,
                    sendError = IllegalArgumentException("Prenumele nu poate fi gol")
                )
                return@launch
            }

            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            val currentDate = dateFormat.format(Date())
            val formattedBirthDate = dateFormat.format(birthDate)
            if (formattedBirthDate == currentDate) {
                uiState = uiState.copy(
                    sendInfo = false,
                    sendError = IllegalArgumentException("Data aleasa este invalida")
                )
                return@launch
            }

            uiState = uiState.copy(sendInfo = true, sendError = null)

            val id = userPreferencesRepository.getId()

            val result = generalInfoService.sendGeneralInformation(
                id, firstName, lastName, birthDate, sex, bloodType
            )

            if(result.isSuccess){
                userPreferencesRepository.saveTrigger(trigger = true)
                uiState = uiState.copy(sendInfo = false, sendCompleted = true)
            } else {
                uiState = uiState.copy(sendInfo = false, sendError = result.exceptionOrNull())
            }
        }
    }

    fun clearAuthenticationError() {
        uiState = uiState.copy(sendError = null)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                GeneralInformationViewModel(
                    app.container.generalInfoService,
                    app.container.userPreferencesRepository
                )
            }
        }
    }
}