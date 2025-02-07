package blood.blooddonation.GeneralInformation

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import blood.blooddonation.R
import blood.blooddonation.authentication.register.RegisterViewModel
import blood.blooddonation.authentication.register.showErrorDialog
import blood.blooddonation.model.enums.BloodType
import blood.blooddonation.model.enums.Sex
import blood.blooddonation.ui.theme.AppColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralInformationScreen(onClose: () -> Unit) {
    val generalInfoViewModel = viewModel<GeneralInformationViewModel>(factory = GeneralInformationViewModel.Factory)
    val  generalInfoUiState = generalInfoViewModel.uiState

    var selectedSex by remember { mutableStateOf(Sex.UNDEFINED) }
    var expandedSexSpinner by remember { mutableStateOf(false) }
    val optionsSexList = Sex.values().toList()

    var selectedBloodType by remember { mutableStateOf(BloodType.UNDEFINED) }
    var expandedBloodTypeSpinner by remember { mutableStateOf(false) }
    val optionsBloodTypeList = BloodType.values().toList()

    var date by remember { mutableStateOf("") }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

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
                        text = stringResource(id = R.string.generalInformation),
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

            var lastName by remember { mutableStateOf("") }
            TextField(
                label = {
                    Text(
                        text = stringResource(id = R.string.lastName),
                        color = AppColor.BlackSoot
                    )
                        },
                value = lastName,
                onValueChange = { lastName = it },
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

            var firstName by remember { mutableStateOf("") }
            TextField(
                label = {
                    Text(
                        text = stringResource(id = R.string.firstName),
                        color = AppColor.BlackSoot
                    )
                        },
                value = firstName,
                onValueChange = { firstName = it },
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(AppColor.WhiteLevenderBlush, shape = RoundedCornerShape(36.dp))
                    .clickable(onClick = { showDialog = true })
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (date.isNotEmpty()) {
                        Text(
                            text = stringResource(id = R.string.selectBirthDate),
                            color = AppColor.BlackSoot,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Text(
                        text = if (date.isNotEmpty()) date else stringResource(id = R.string.selectBirthDate),
                        color = AppColor.BlackSoot,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    if (date.isEmpty()) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            if (showDialog) {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    context,
                    { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                        date = "$dayOfMonth.${month + 1}.$year"
                        showDialog = false
                    },
                    year,
                    month,
                    day
                )

                datePickerDialog.setOnDismissListener {
                    showDialog = false
                }

                datePickerDialog.datePicker.maxDate = calendar.timeInMillis

                datePickerDialog.show()
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedSexSpinner,
                onExpandedChange = { expandedSexSpinner = it }
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { expandedSexSpinner = !expandedSexSpinner })
                ) {
                    TextField(
                        value = selectedSex.displayName,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        label = {
                            Text(
                                text = stringResource(id = R.string.selectSex),
                                color = AppColor.BlackSoot
                            )
                                },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSexSpinner)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = AppColor.BlackSoot,
                            cursorColor = AppColor.BlackSoot,
                            containerColor = AppColor.WhiteLevenderBlush,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(36.dp)
                    )
                }
                ExposedDropdownMenu(
                    expanded = expandedSexSpinner,
                    onDismissRequest = { expandedSexSpinner = false },
                    modifier = Modifier
                        .background(AppColor.WhiteLevenderBlush),
                ) {
                    optionsSexList.forEach { sex ->
                        DropdownMenuItem(
                            onClick = {
                                selectedSex = sex
                                expandedSexSpinner = false
                            },
                            modifier = Modifier
                                .background(AppColor.WhiteLevenderBlush),
                        ) {
                            Text(
                                text = sex.displayName,
                                color = AppColor.BlackSoot
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedBloodTypeSpinner,
                onExpandedChange = { expandedBloodTypeSpinner = it }
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { expandedBloodTypeSpinner = !expandedBloodTypeSpinner })) {
                    TextField(
                        value = selectedBloodType.displayName,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        label = {
                            Text(
                                text = stringResource(id = R.string.selectBloodType),
                                color = AppColor.BlackSoot
                            )
                                },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedBloodTypeSpinner)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = AppColor.BlackSoot,
                            cursorColor = AppColor.BlackSoot,
                            containerColor = AppColor.WhiteLevenderBlush,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(36.dp)
                    )
                }
                ExposedDropdownMenu(
                    expanded = expandedBloodTypeSpinner,
                    onDismissRequest = { expandedBloodTypeSpinner = false },
                    modifier = Modifier
                        .background(AppColor.WhiteLevenderBlush),
                ) {
                    optionsBloodTypeList.forEach { bloodType ->
                        DropdownMenuItem(
                            onClick = {
                                selectedBloodType = bloodType
                                expandedBloodTypeSpinner = false
                            },
                            modifier = Modifier
                                .background(AppColor.WhiteLevenderBlush),
                        ) {
                            Text(
                                text = bloodType.displayName,
                                color = AppColor.BlackSoot
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(70.dp))

            Button(
                onClick = {
                    val parsedDate = parseDate(date) ?: Date()
                    generalInfoViewModel.saveGeneralInfo(
                    firstName = firstName,
                    lastName = lastName,
                    birthDate = parsedDate,
                    sex = selectedSex,
                    bloodType = selectedBloodType
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.RedFirebrick,
                    contentColor = AppColor.BlackSoot
                ),
            ) {
                Text(
                    stringResource(id = R.string.send),
                    fontSize = 20.sp
                )
            }

            if (generalInfoUiState.sendInfo) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                )
            }
            if (generalInfoUiState.sendError != null) {
                showErrorDialog.value = true
            }
        }
    }

    if(showErrorDialog.value) {
        showErrorDialog(
            showErrorDialog,
            generalInfoViewModel
        )
    }

    LaunchedEffect(generalInfoUiState.sendCompleted) {
        if (generalInfoUiState.sendCompleted) {
            onClose()
        }
    }
}

fun parseDate(dateStr: String): Date? {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return try {
        format.parse(dateStr)
    } catch (e: Exception) {
        null
    }
}

@Composable
fun showErrorDialog(showDialog: MutableState<Boolean>,
                    generalInfoViewModel: GeneralInformationViewModel
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = "Eroare: ${generalInfoViewModel.uiState.sendError!!.message}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColor.BlackSoot
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    showDialog.value = false
                    generalInfoViewModel.clearAuthenticationError()
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
fun GeneralInformationScreenPreview() {
    GeneralInformationScreen(onClose = {})
}