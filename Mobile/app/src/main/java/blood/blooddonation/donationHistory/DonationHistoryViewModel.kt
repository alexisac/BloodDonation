package blood.blooddonation.donationHistory

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import blood.blooddonation.model.DonationHistoryEntry
import blood.blooddonation.service.DonationHistoryService
import kotlinx.coroutines.launch

data class DonationHistoryUiState(
    val error: Throwable? = null,
    val inProgress: Boolean = false,
    val complete: Boolean = false,
    val donationHistoryList: List<DonationHistoryEntry> = emptyList()
)

class DonationHistoryViewModel (
    private val donationHistoryService: DonationHistoryService
): ViewModel() {
    var uiState: DonationHistoryUiState by mutableStateOf(DonationHistoryUiState())

    fun getAllDonationHistory(idUser: String?) {
        viewModelScope.launch {
            uiState = uiState.copy(error = null, inProgress = true)

            val result = idUser?.let { donationHistoryService.getAllDonationHistory(it) }
            if (result != null) {
                result.onSuccess {
                    list -> uiState = uiState.copy(
                        error = null,
                        complete = true,
                        inProgress = false,
                        donationHistoryList = list
                    )
                }.onFailure {
                    uiState = uiState.copy(
                        error = result.exceptionOrNull(),
                        inProgress = false
                    )
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                DonationHistoryViewModel(app.container.donationHistoryService)
            }
        }
    }
}