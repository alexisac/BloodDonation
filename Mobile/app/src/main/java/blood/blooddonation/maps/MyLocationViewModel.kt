package blood.blooddonation.maps

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyLocationViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<Location?>(null)
    val uiState: StateFlow<Location?> get() = _uiState

    init {
        collectLocation()
    }

    private fun collectLocation() {
        viewModelScope.launch {
            LocationMonitor(getApplication()).currentLocation.collect {
                _uiState.value = it;
            }
        }
    }

    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MyLocationViewModel(application)
            }
        }
    }
}
