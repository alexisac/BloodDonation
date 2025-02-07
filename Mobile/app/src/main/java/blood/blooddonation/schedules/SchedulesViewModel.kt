package blood.blooddonation.schedules

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import blood.blooddonation.model.ScheduleCenter
import blood.blooddonation.service.ScheduleService
import kotlinx.coroutines.launch

data class SchedulesScreenUiState(
    val error: Throwable? = null,
    val inProgress: Boolean = false,
    val complete: Boolean = false,
    val schedulesList: List<ScheduleCenter> = emptyList()
)

class SchedulesViewModel (
    private val scheduleService: ScheduleService
): ViewModel() {
    var uiState: SchedulesScreenUiState by mutableStateOf(SchedulesScreenUiState())

    fun getBookedForUser(idUser: String?){
        viewModelScope.launch {
            uiState = uiState.copy(error = null, inProgress = true)

            val result = scheduleService.getBookedForUser(idUser!!)
            result.onSuccess {
                    list -> uiState = uiState.copy(
                error = null,
                complete = true,
                inProgress = false,
                schedulesList = list
            )
            }.onFailure {
                uiState = uiState.copy(
                    error = result.exceptionOrNull(),
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
                SchedulesViewModel(app.container.scheduleService)
            }
        }
    }
}