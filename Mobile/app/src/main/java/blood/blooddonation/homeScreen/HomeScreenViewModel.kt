package blood.blooddonation.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import blood.blooddonation.model.GeneralInfo
import blood.blooddonation.preferences.UserPreferencesRepository
import blood.blooddonation.service.GeneralInfoService
import kotlinx.coroutines.launch

data class HomeScreenUiState(
    val idUser: String = "",
    val generalInfo: GeneralInfo? = null,
    val error: Throwable? = null,
    val inProgress: Boolean = false,
    val complete: Boolean = false,
)

class HomeScreenViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val generalInfoService: GeneralInfoService
):ViewModel() {

    var uiState: HomeScreenUiState by mutableStateOf(HomeScreenUiState())

    init {
        getUserId()
    }

    fun getUserId() {
        viewModelScope.launch {
            uiState = uiState.copy(idUser = userPreferencesRepository.getId())
            getGeneralInformation(userPreferencesRepository.getId())
        }
    }

    private fun getGeneralInformation(idUser: String?) {
        viewModelScope.launch {
            uiState = uiState.copy(error = null, inProgress = true)

            try {
                val result = idUser?.let { generalInfoService.getGeneralInformation(it) }
                if (result != null) {
                    result.onSuccess { generalInfo ->
                        uiState = uiState.copy(
                            generalInfo = generalInfo,
                            complete = true,
                            inProgress = false
                        )
                    }.onFailure { exception ->
                        uiState = uiState.copy(
                            error = exception,
                            inProgress = false
                        )
                    }
                } else {
                    uiState = uiState.copy(
                        error = IllegalArgumentException("User ID is null"),
                        inProgress = false
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e,
                    inProgress = false
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                HomeScreenViewModel(
                    app.container.userPreferencesRepository,
                    app.container.generalInfoService
                )
            }
        }
    }
}