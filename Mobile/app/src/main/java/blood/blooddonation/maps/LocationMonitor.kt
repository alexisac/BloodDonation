package blood.blooddonation.maps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class LocationMonitor(val context: Context) {
    @SuppressLint("MissingPermission")
    val currentLocation: Flow<Location> = callbackFlow<Location> {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    trySend(location).isSuccess
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            LocationRequest.Builder(10000).build(),
            locationCallback,
            Looper.getMainLooper()
        )

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
}