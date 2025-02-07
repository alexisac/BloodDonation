package blood.blooddonation.verifyEligibility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import blood.blooddonation.R
import blood.blooddonation.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(userId: String?, nextQuestion: (userId: String) -> Unit, goBack: () -> Unit) {
    val verifyEligibilityViewModel = viewModel<VerifyEligibilityViewModel>(factory = VerifyEligibilityViewModel.Factory)
    val verifyEligibilityUiState = verifyEligibilityViewModel.uiState

    val selectedButton = remember { mutableStateOf(-1) }
    val selectedAnswerPoints = remember { mutableStateOf(-1) }

    val showExitDialog = remember { mutableStateOf(false) }
    val showFinishDialog = remember { mutableStateOf(false) }

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
                        text = stringResource(id = R.string.question) + "  " +
                                verifyEligibilityUiState.noOfCurrentQuestion.toString() + " / " +
                                verifyEligibilityUiState.noTotalOfQuestion.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 32.sp,
                            color = AppColor.BlackSoot
                        )
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        onClick = { showExitDialog.value = true }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AppColor.BlackSoot
                        )
                    }
                    if (showExitDialog.value) {
                        showExitConfirmationDialog(showExitDialog, verifyEligibilityViewModel, goBack)
                    }
                },
                actions = {
                    Spacer(modifier = Modifier.width(48.dp))
                },
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(gradient)
    ) {

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(bottom = 100.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = verifyEligibilityUiState.question?.text ?: "",
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp,
                    color = AppColor.BlackSoot
                )

                Spacer(modifier = Modifier.height(50.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    verifyEligibilityUiState.question?.answers?.forEachIndexed { index, answer ->
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = {
                                selectedButton.value = index
                                selectedAnswerPoints.value = answer.score
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    if (selectedButton.value == index) AppColor.RedCarnelian
                                    else AppColor.RedGrenaline,
                                contentColor =
                                    if (selectedButton.value == index) AppColor.WhiteLevenderBlush
                                    else AppColor.BlackSoot
                            ),
                        ) {
                            Text(answer.text)
                        }
                    }
                }
            }

            Button(
                onClick = {
                    verifyEligibilityViewModel.updatePoints(selectedAnswerPoints.value)
                    nextQuestion(userId!!)
                },
                enabled = selectedButton.value != -1 && selectedAnswerPoints.value != -1,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.RedCarnelian,
                    contentColor = AppColor.WhiteLevenderBlush
                )
            ) {
                Icon(Icons.Filled.ArrowForward, contentDescription = "Next")
            }
        }
    }

    LaunchedEffect(key1 = true) {
        verifyEligibilityViewModel.getNewQuestion()
        if(verifyEligibilityViewModel.uiState.question == null &&
        verifyEligibilityViewModel.uiState.noTotalOfQuestion != 0){
            verifyEligibilityViewModel.decideEligibilityType(idUser = userId!!)
            showFinishDialog.value = true
        }
    }

    if(showFinishDialog.value) {
        showEligibilityTypeDialog(
            showFinishDialog,
            verifyEligibilityViewModel,
            goBack
        )
    }
}

@Composable
fun showExitConfirmationDialog(showDialog: MutableState<Boolean>,
                               verifyEligibilityViewModel: VerifyEligibilityViewModel,
                               goBack: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = "Ești sigur că vrei să părăsești verificarea?",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColor.BlackSoot
            )
        },
        text = {
            Text(
                "Toate informațiile completate se vor pierde!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColor.BlackSoot
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    showDialog.value = false
                    verifyEligibilityViewModel.resetPoints()
                    verifyEligibilityViewModel.resetQuestions()
                    goBack()
                },
                modifier = Modifier.fillMaxWidth(0.48f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.RedCarnelian,
                    contentColor = AppColor.WhiteLevenderBlush
                )
            ) {
                Text(
                    text = "Da",
                    textAlign = TextAlign.Center
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    showDialog.value = false
                },
                modifier = Modifier.fillMaxWidth(0.49f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.RedCarnelian,
                    contentColor = AppColor.WhiteLevenderBlush
                )
            ) {
                Text(
                    text = "Nu",
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
fun showEligibilityTypeDialog(showDialog: MutableState<Boolean>,
                              verifyEligibilityViewModel: VerifyEligibilityViewModel,
                              goBack: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = verifyEligibilityViewModel.uiState.eligibilityType.displayName,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = AppColor.BlackSoot
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    showDialog.value = false
                    verifyEligibilityViewModel.resetPoints()
                    verifyEligibilityViewModel.resetQuestions()
                    goBack()
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
fun QuestionScreenPreview() {
    QuestionScreen(userId = null, nextQuestion = {}, goBack = {})
}