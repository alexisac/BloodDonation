package blood.blooddonation.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MyMap(lat: Double, long: Double, modifier: Modifier = Modifier) {
    val markerState = rememberMarkerState(position = LatLng(lat, long))
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 14f)
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = MarkerState(position = markerState.position),
            title = "User location title",
            snippet = "User location",
        )
    }
}