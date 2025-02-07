package blood.blooddonation

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import blood.blooddonation.utils.TAG
import blood.blooddonation.ui.theme.BloodDonationTheme
import kotlinx.coroutines.launch
import blood.blooddonation.permissions.Permission
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d(TAG, "on create")
            BloodDonation {
                Permission(
                    permissions = listOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    relationaleText = "Avem nevoie de locația dispozitivului",
                    dismissedText = ""
                ) {
                    AppNavHost()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            (application as BloodDonation).container.itemRepository.openWsClient()
        }
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch {
            (application as BloodDonation).container.itemRepository.closeWsClient()
        }
    }
}

@Composable
fun BloodDonation(content: @Composable () -> Unit) {
    Log.d("BloodDonation", "recompose")
    BloodDonationTheme {
        Surface {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewBloodDonation() {
    BloodDonation {
        AppNavHost()
    }
}
