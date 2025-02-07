package blood.blooddonation.permissions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import blood.blooddonation.ui.theme.AppColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@ExperimentalPermissionsApi
@Composable
fun Permission(
    permissions: List<String>,
    relationaleText: String,
    dismissedText: String,
    content: @Composable () -> Unit = {}
) {
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)
    PermissionsRequired(
        multiplePermissionsState = permissionsState,
        permissionsNotGrantedContent = {
            PermissionsNotGranted(
                text = relationaleText,
                onRequestPermission = { permissionsState.launchMultiplePermissionRequest() }
            )
        },
        permissionsNotAvailableContent = {
            PermissionsNotAvailable(
                text = dismissedText
            )
        },
        content = content
    )
}

@Composable
private fun PermissionsNotGranted(
    text: String,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColor.BlackSoot
            )
        },
        confirmButton = {
            Button(
                onClick = onRequestPermission,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.RedCarnelian,
                    contentColor = AppColor.WhiteLevenderBlush
                )
            ) {
                Text(
                    text ="Okay",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        containerColor = AppColor.WhiteLevenderBlush,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun PermissionsNotAvailable(text: String) {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize()) {
        Text(text)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                context.startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                )
            }
        ) {
            Text("Open settings")
        }
    }
}