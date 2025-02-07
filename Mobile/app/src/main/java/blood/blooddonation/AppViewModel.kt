package blood.blooddonation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import blood.blooddonation.item.ItemRepository
import blood.blooddonation.preferences.UserPreferencesRepository

class AppViewModel(
    private val userPreferenceRepository: UserPreferencesRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {

    fun setToken(token: String) {
        itemRepository.setToken(token)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodDonation)
                AppViewModel(
                    app.container.userPreferencesRepository,
                    app.container.itemRepository
                )
            }
        }
    }
}