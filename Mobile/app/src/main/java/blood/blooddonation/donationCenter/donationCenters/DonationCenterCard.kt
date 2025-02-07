package blood.blooddonation.donationCenter.donationCenters

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import blood.blooddonation.maps.MyLocationViewModel
import blood.blooddonation.model.DonationCenter
import blood.blooddonation.ui.theme.AppColor
import kotlin.math.roundToInt
import blood.blooddonation.R

@Composable
fun DonationCenterCard(
    userId: String?,
    donationCenter: DonationCenter,
    onClick: (DonationCenter) -> Unit
) {
    val context = LocalContext.current
    val myLocationViewModel = viewModel<MyLocationViewModel>(
        factory = MyLocationViewModel.Factory(context.applicationContext as Application)
    )
    val location by myLocationViewModel.uiState.collectAsState()
    var distance by remember { mutableStateOf<Float?>(null) }
    Log.e("Alex", location?.latitude.toString() + " | " + location?.longitude.toString())
    LaunchedEffect(location) {
        location?.let {
            val results = FloatArray(1)
            Location.distanceBetween(
                it.latitude, it.longitude,
                donationCenter.latitude.toDouble(), donationCenter.longitude.toDouble(),
                results
            )
            distance = results[0]
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(donationCenter) },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(AppColor.WhiteLevenderBlush)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = donationCenter.name,
                color = AppColor.BlackSoot,
                style = TextStyle(
                    fontSize = 20.sp
                )
            )

            distance?.let {
                val distanceText = if (it >= 1000) {
                    stringResource(id = R.string.distanceToCenterKilometers, it / 1000)
                } else {
                    stringResource(id = R.string.distanceToCenterMeters, it.roundToInt())
                }
                Text(
                    text = distanceText,
                    color = AppColor.BlackSoot,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}