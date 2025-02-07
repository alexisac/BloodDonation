package blood.blooddonation.verifyEligibility

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import blood.blooddonation.model.Question
import blood.blooddonation.model.enums.EligibilityType
import blood.blooddonation.service.VerifyEligibilityService
import kotlinx.coroutines.launch

data class VerifyEligibilityUiState(
    val question: Question? = null,
    val eligibilityType: EligibilityType = EligibilityType.UNDEFINED,
    val error: Throwable? = null,
    val inProgress: Boolean = false,
    val complete: Boolean = false,
    val noTotalOfQuestion: Int = 0,
    val noOfCurrentQuestion: Int = 0,
)

class VerifyEligibilityViewModel(
    private val verifyEligibilityService: VerifyEligibilityService
):ViewModel() {
    var uiState: VerifyEligibilityUiState by mutableStateOf(VerifyEligibilityUiState())

    fun getNewQuestion() {
        viewModelScope.launch {
            val result = verifyEligibilityService.getNewQuestion()
            val noOfCurrentQuestion = verifyEligibilityService.getNoOfQuestion()
            val noTotalOfQuestion = verifyEligibilityService.getNoTotalOfQuestions()
            uiState = uiState.copy(
                question = result,
                eligibilityType = EligibilityType.UNDEFINED,
                noOfCurrentQuestion = noOfCurrentQuestion,
                noTotalOfQuestion = noTotalOfQuestion
            )
        }
    }

    fun updatePoints(points: Int) {
        viewModelScope.launch {
            verifyEligibilityService.updatePoints(points)
        }
    }

    fun decideEligibilityType(idUser: String) {
        viewModelScope.launch {
            val result = verifyEligibilityService.decideEligibilityType()
            uiState = uiState.copy(question = null, eligibilityType = result)
            saveEligibilityType(idUser = idUser, eligibilityType = result)
        }
    }

    private fun saveEligibilityType(idUser: String, eligibilityType: EligibilityType) {
        viewModelScope.launch {
            verifyEligibilityService.saveEligibilityType(
                idUser = idUser,
                eligibilityType = eligibilityType.intValue
            )
        }
    }

    fun getEligibilityTypeForUser(idUser: String?) {
        viewModelScope.launch {
            uiState = uiState.copy(error = null, inProgress = true)

            val result = idUser?.let { verifyEligibilityService.getEligibilityTypeForUser(idUser = it) }
            if(result != null) {
                result.onSuccess {
                    eligibilityType -> uiState = uiState.copy(
                        error = null,
                        complete = true,
                        inProgress = false,
                        eligibilityType = eligibilityType
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

    fun resetPoints(){
        viewModelScope.launch {
            verifyEligibilityService.resetPoints()
        }
    }

    fun resetQuestions(){
        viewModelScope.launch {
            verifyEligibilityService.resetQuestions()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                VerifyEligibilityViewModel(app.container.verifyEligibilityService)
            }
        }
    }
}