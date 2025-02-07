package blood.blooddonation.donationCenter.donationCenters

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import blood.blooddonation.R
import blood.blooddonation.maps.MyMap
import blood.blooddonation.ui.theme.AppColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationCenterPage(
    userId: String?,
    itemId: String?,
    goBack: (userId: String?) -> Unit
) {
    val donationCenterViewModel =
        viewModel<DonationCentersViewModel>(factory = DonationCentersViewModel.Factory)
    val donationCenterUiState = donationCenterViewModel.uiState
    val currentCenter =
        if (itemId != null && donationCenterUiState.donationCentersList.isNotEmpty()) {
            donationCenterUiState.donationCentersList.find { it.id == itemId }
        } else {
            null
        }

    var date by remember { mutableStateOf("") }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

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
                        text = currentCenter?.name ?: "Error",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 10.dp),
                        textAlign = TextAlign.Center,
                        color = AppColor.BlackSoot
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        onClick = { goBack(userId) }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AppColor.BlackSoot
                        )
                    }
                },
                actions = {
                    Spacer(modifier = Modifier.width(48.dp))
                }
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if(currentCenter != null) {
                MyMap(
                    lat = currentCenter.latitude.toDouble(),
                    long = currentCenter.longitude.toDouble(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = stringResource(id = R.string.bookedForDonation),
                color = AppColor.BlackSoot
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
                            text = stringResource(id = R.string.selectDate),
                            color = AppColor.BlackSoot,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Text(
                        text = if (date.isNotEmpty()) date else stringResource(id = R.string.selectDate),
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
                        selectedTime = ""
                        donationCenterViewModel.getBookedDates(date, itemId!!)
                        showDialog = false
                    },
                    year,
                    month,
                    day
                )

                datePickerDialog.setOnDismissListener {
                    showDialog = false
                }

                datePickerDialog.datePicker.minDate = calendar.timeInMillis + (1000 * 60 * 60 * 24)

                datePickerDialog.show()
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (date.isNotEmpty()) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { expanded = !expanded })
                    ) {
                        TextField(
                            value = selectedTime,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            label = {
                                Text(
                                    text = stringResource(id = R.string.selectHour),
                                    color = AppColor.BlackSoot
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = AppColor.WhiteLevenderBlush,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                textColor = AppColor.BlackSoot
                            ),
                            shape = RoundedCornerShape(36.dp)
                        )
                    }
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(AppColor.WhiteLevenderBlush),
                    ) {
                        generateTimes().forEach { time ->
                            val formattedDateTime = "$date $time"
                            val isBooked = donationCenterUiState.getBookedDates.contains(formattedDateTime)
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = time,
                                        color = if (isBooked) Color.Gray else AppColor.BlackSoot
                                    )
                                },
                                onClick = {
                                    if (!isBooked) {
                                        selectedTime = time
                                        expanded = false
                                    }
                                },
                                enabled = !isBooked
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    donationCenterViewModel.saveScheduleDate(userId!!, "$date $selectedTime", itemId!!)
                    showSuccessDialog = true
                          },
                enabled = selectedTime.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.RedGrenaline,
                    contentColor = AppColor.WhiteLevenderBlush
                )
            ) {
                Text(text = stringResource(id = R.string.sendRequest))
            }

            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { showSuccessDialog = false },
                    title = {
                        Text(
                            text = "Programarea a fost făcută cu succes",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = AppColor.BlackSoot
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showSuccessDialog = false
                                goBack(userId)
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
        }
    }

    LaunchedEffect(key1 = true) {
        donationCenterViewModel.getAllDonationCenters()
    }
}

private fun generateTimes(): List<String> {
    val times = mutableListOf<String>()
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 8)
    calendar.set(Calendar.MINUTE, 0)

    while (calendar.get(Calendar.HOUR_OF_DAY) < 17) {
        times.add(formatter.format(calendar.time))
        calendar.add(Calendar.MINUTE, 20)
    }

    return times
}