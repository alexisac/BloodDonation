package blood.blooddonation.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.BloodDonation
import kotlinx.coroutines.flow.Flow

class UserPreferencesViewModel(private val userPreferencesRepository: UserPreferencesRepository)
    : ViewModel() {

        val uiState: Flow<UserPreferences> = userPreferencesRepository.userPreferencesStream

        companion object {
            val Factory: ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    val app =
                        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                                as BloodDonation)
                    UserPreferencesViewModel(app.container.userPreferencesRepository)
                }
            }
        }

}