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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import blood.blooddonation.R
import blood.blooddonation.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEligibilityScreen(
    userId: String?,
    toQuestions: (userId: String) -> Unit,
    goBack: () -> Unit
) {
    val verifyEligibilityViewModel = viewModel<VerifyEligibilityViewModel>(factory = VerifyEligibilityViewModel.Factory)
    val verifyEligibilityUiState = verifyEligibilityViewModel.uiState

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
                        text = stringResource(id = R.string.searchVeriFyEligibility),
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
                        onClick = { goBack() }
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
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.matchParentSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = verifyEligibilityUiState.eligibilityType.displayName,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp,
                    color = AppColor.BlackSoot
                )

                if (verifyEligibilityUiState.inProgress) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                    )
                }
            }

            Button(
                onClick = { toQuestions(userId!!) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.RedFirebrick,
                    contentColor = AppColor.BlackSoot
                ),
            ) {
                Text(
                    stringResource(id = R.string.startVerify),
                    style = TextStyle(
                        fontSize = 20.sp
                    )
                )
            }
        }
    }

    LaunchedEffect(key1 = true) {
        verifyEligibilityViewModel.getEligibilityTypeForUser(userId)
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyEligibilityScreenPreview() {
    VerifyEligibilityScreen(userId = null, toQuestions = {}, goBack = {})
}