package blood.blooddonation.authentication.register
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import blood.blooddonation.R
import blood.blooddonation.ui.theme.AppColor
import blood.blooddonation.verifyEligibility.VerifyEligibilityViewModel
import blood.blooddonation.verifyEligibility.showEligibilityTypeDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(toLogin: () -> Unit, toResetPass: () -> Unit, onClose: () -> Unit) {
    val registerViewModel = viewModel<RegisterViewModel>(factory = RegisterViewModel.Factory)
    val registerUiState = registerViewModel.uiState

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

    val loginText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 20.sp)) {
            append("  Ai deja cont? ")
        }
        pushStringAnnotation(tag = "login", annotation = "login")
        withStyle(style = SpanStyle(color = AppColor.RedCarnelian, fontSize = 20.sp)) {
            append(" Autentificare")
        }
        pop()
    }

    val resetPassText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 20.sp)) {
            append("Ți-ai uitat parola? ")
        }
        pushStringAnnotation(tag = "resetPass", annotation = "resetPass")
        withStyle(style = SpanStyle(color = AppColor.RedCarnelian, fontSize = 20.sp)) {
            append(" Resetare parolă")
        }
        pop()
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(AppColor.RedGrenaline, AppColor.WhiteLevenderBlush),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.register),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp,
                        color = AppColor.BlackSoot
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(gradient)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(50.dp))

            var email by remember { mutableStateOf("") }
            TextField(
                label = {
                    Text(
                        text = stringResource(id = R.string.emailLabel),
                        color = AppColor.BlackSoot
                    )
                        },
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(36.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = AppColor.BlackSoot,
                    cursorColor = AppColor.BlackSoot,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = AppColor.WhiteLevenderBlush
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            var cnp by remember { mutableStateOf("") }
            TextField(
                label = {
                    Text(
                        text = stringResource(id = R.string.cnpLabel),
                        color = AppColor.BlackSoot
                    )
                        },
                value = cnp,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        cnp = it
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(36.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = AppColor.BlackSoot,
                    cursorColor = AppColor.BlackSoot,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = AppColor.WhiteLevenderBlush
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            var password by remember { mutableStateOf("") }
            TextField(
                label = {
                    Text(
                        text = stringResource(id = R.string.passwordLabel),
                        color = AppColor.BlackSoot
                    )
                        },
                visualTransformation =
                        if (passwordVisibility) VisualTransformation.None
                        else PasswordVisualTransformation(),
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisibility) Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff
                    val description = if (passwordVisibility) "hidePassword"
                                        else "showPassword"
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(image, description, tint = AppColor.BlackSoot)
                    }
                },
                shape = RoundedCornerShape(36.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = AppColor.BlackSoot,
                    cursorColor = AppColor.BlackSoot,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = AppColor.WhiteLevenderBlush
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            var confirmPassword by remember { mutableStateOf("") }
            TextField(
                label = {
                    Text(
                        text = stringResource(id = R.string.confirmPasswordLabel),
                        color = AppColor.BlackSoot
                    )
                        },
                visualTransformation =
                        if (confirmPasswordVisibility) VisualTransformation.None
                        else PasswordVisualTransformation(),
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (confirmPasswordVisibility) Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff
                    val description = if (confirmPasswordVisibility) "hidePassword"
                                        else "showPassword"
                    IconButton(onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }) {
                        Icon(image, description, tint = AppColor.BlackSoot)
                    }
                },
                shape = RoundedCornerShape(36.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = AppColor.BlackSoot,
                    cursorColor = AppColor.BlackSoot,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = AppColor.WhiteLevenderBlush
                )
            )

            Spacer(modifier = Modifier.height(100.dp))

            Button(
                onClick = { registerViewModel.register(email, cnp, password, confirmPassword) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.RedFirebrick,
                    contentColor = AppColor.BlackSoot
                ),
            ) {
                Text(
                    stringResource(id = R.string.register),
                    fontSize = 20.sp
                )
            }

            ClickableText(
                text = loginText,
                onClick = { offset ->
                    loginText.getStringAnnotations(
                        tag = "login",
                        start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let {
                            toLogin()
                        }
                },
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(6.dp))

            ClickableText(
                text = resetPassText,
                onClick = { offset ->
                    resetPassText.getStringAnnotations(
                        tag = "resetPass",
                        start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let {
                            toResetPass()
                        }
                },
                modifier = Modifier
            )

            if (registerUiState.isAuthenticating) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                )
            }
            if (registerUiState.authenticationError != null) {
                showErrorDialog.value = true
            }
        }
    }

    if(showErrorDialog.value) {
        showErrorDialog(
            showErrorDialog,
            registerViewModel
        )
    }

    LaunchedEffect(registerUiState.authenticationCompleted) {
        if (registerUiState.authenticationCompleted) {
            onClose()
        }
    }
}
@Composable
fun showErrorDialog(showDialog: MutableState<Boolean>,
                    registerViewModel: RegisterViewModel
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = "Eroare: ${registerViewModel.uiState.authenticationError!!.message}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColor.BlackSoot
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    showDialog.value = false
                    registerViewModel.clearAuthenticationError()
                },
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

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(toLogin = {}, toResetPass = {}, onClose = {})
}