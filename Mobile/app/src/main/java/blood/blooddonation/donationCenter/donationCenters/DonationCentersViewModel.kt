package blood.blooddonation.donationCenter.donationCenters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import blood.blooddonation.model.DonationCenter
import blood.blooddonation.service.DonationCentersService
import kotlinx.coroutines.launch

data class DonationCentersUiState(
    val error: Throwable? = null,
    val inProgress: Boolean = false,
    val complete: Boolean = false,
    val donationCentersList: List<DonationCenter> = emptyList(),
    val getBookedDates: List<String> = emptyList()
)

class DonationCentersViewModel(
    private val donationCentersService: DonationCentersService
) : ViewModel() {
    var uiState: DonationCentersUiState by mutableStateOf(DonationCentersUiState())

    fun getAllDonationCenters() {
        viewModelScope.launch {
            uiState = uiState.copy(error = null, inProgress = true)

            val result = donationCentersService.getAllDonationCenters()
            result.onSuccess {
                list -> uiState = uiState.copy(
                    error = null,
                    complete = true,
                    inProgress = false,
                    donationCentersList = list
                )
            }.onFailure {
                uiState = uiState.copy(
                    error = result.exceptionOrNull(),
                    inProgress = false
                )
            }
        }
    }

    fun getBookedDates(date: String, centerId: String) {
        viewModelScope.launch {
            uiState = uiState.copy(error = null, inProgress = true)

            val result = donationCentersService.getBookedDates(date, centerId)
            result.onSuccess {
                list -> uiState = uiState.copy(
                    error = null,
                    complete = true,
                    inProgress = false,
                    getBookedDates = list
                )
            }.onFailure {
                uiState = uiState.copy(
                    error = result.exceptionOrNull(),
                    inProgress = false
                )
            }
        }
    }

    fun saveScheduleDate(idUser: String, date: String, centerId: String) {
        viewModelScope.launch {
            donationCentersService.saveScheduleDate(idUser, date, centerId)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                DonationCentersViewModel(app.container.donationCentersService)
            }
        }
    }
}