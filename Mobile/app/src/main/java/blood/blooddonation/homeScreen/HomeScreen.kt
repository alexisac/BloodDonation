package blood.blooddonation.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PublishedWithChanges
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import blood.blooddonation.R
import blood.blooddonation.model.enums.convertIntToBloodType
import blood.blooddonation.model.enums.convertIntToGenderType
import blood.blooddonation.ui.theme.AppColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    toDonationCenters: (userId: String) -> Unit,
    toDonationHistory: (userId: String) -> Unit,
    toVerifyEligibility: (userId: String) -> Unit,
    toChatLive: () -> Unit,
    toPdfDocument: (userId: String) -> Unit,
    toSchedules: (userId: String) -> Unit,
    logout: () -> Unit
) {
    val homeScreenViewModel = viewModel<HomeScreenViewModel>(factory = HomeScreenViewModel.Factory)
    val homeScreenUiState = homeScreenViewModel.uiState
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val gradient = Brush.verticalGradient(
        colors = listOf(AppColor.RedGrenaline, AppColor.WhiteLevenderBlush),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = AppColor.WhiteLevenderBlush
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.meniuDrawer),
                        color = AppColor.BlackSoot,
                        style = TextStyle(
                            fontSize = 36.sp,
                        )
                    )
                    Spacer(modifier = Modifier.height(50.dp))

                    DrawerButton(
                        onClick = { toDonationCenters(homeScreenUiState.idUser) },
                        text = stringResource(id = R.string.searchDonationCenter),
                        icon = Icons.Filled.Apartment
                    )

                    DrawerButton(
                        onClick = { toSchedules(homeScreenUiState.idUser) },
                        text = stringResource(id = R.string.schedulesDonationCenter),
                        icon = Icons.Filled.CalendarMonth
                    )

                    DrawerButton(
                        onClick = { toDonationHistory(homeScreenUiState.idUser) },
                        text = stringResource(id = R.string.searchDonationHistory),
                        icon = Icons.Filled.Bloodtype
                    )

                    DrawerButton(
                        onClick = { toVerifyEligibility(homeScreenUiState.idUser) },
                        text = stringResource(id = R.string.searchVeriFyEligibility),
                        icon = Icons.Filled.PublishedWithChanges
                    )

                    DrawerButton(
                        onClick = { toChatLive() },
                        text = stringResource(id = R.string.chatLive),
                        icon = Icons.Filled.Forum
                    )

                    DrawerButton(
                        onClick = { toPdfDocument(homeScreenUiState.idUser) },
                        text = stringResource(id = R.string.generatePdf),
                        icon = Icons.Filled.PictureAsPdf
                    )

                    DrawerButton(
                        onClick = { logout() },
                        text = stringResource(id = R.string.logout),
                        icon = Icons.AutoMirrored.Filled.ExitToApp
                    )
                }
            }
        },

        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.myProfile),
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
                        ),
                        navigationIcon = {
                            IconButton(
                                modifier = Modifier
                                    .padding(top = 20.dp),
                                onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) { drawerState.open() }
                                        else { drawerState.close() }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    modifier = Modifier.size(30.dp),
                                    tint = AppColor.BlackSoot
                                )
                            }
                        },
                        actions = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(top = 20.dp)
                            ) {
                                Text(
                                    text = homeScreenUiState.generalInfo?.points.toString(),
                                    fontSize = 25.sp,
                                    color = AppColor.BlackSoot
                                )
                                Icon(
                                    Icons.Default.Paid,
                                    contentDescription = "Points",
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.Yellow
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))
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

                    ProfileItem(
                        label = stringResource(id = R.string.lastName),
                        value = homeScreenUiState.generalInfo?.lastName,
                        icon = Icons.Default.Person
                    )
                    ProfileItem(
                        label = stringResource(id = R.string.firstName),
                        value = homeScreenUiState.generalInfo?.firstName,
                        icon = Icons.Default.PersonOutline
                    )
                    ProfileItem(
                        label = stringResource(id = R.string.birthDate),
                        value = homeScreenUiState.generalInfo?.birthDate,
                        icon = Icons.Default.CalendarToday
                    )
                    ProfileItem(
                        label = stringResource(id = R.string.sex),
                        value = homeScreenUiState.generalInfo?.sex?.toIntOrNull()?.let
                        { convertIntToGenderType(it).displayName },
                        icon = Icons.Default.Info
                    )
                    ProfileItem(
                        label = stringResource(id = R.string.bloodType),
                        value = homeScreenUiState.generalInfo?.bloodType?.toIntOrNull()?.let
                        { convertIntToBloodType(it).displayName },
                        icon = Icons.Default.Bloodtype
                    )
                }
            }
        }
    )
}

@Composable
fun ProfileItem(label: String, value: String?, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 20.dp)
            .padding(start = 80.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = AppColor.BlackSoot
        )
        Spacer(Modifier.width(16.dp))
        Column{
            Text(
                text = label,
                color = AppColor.BlackSoot,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Text(
                text = value ?: "-----",
                color = AppColor.BlackSoot,
                modifier = Modifier.padding(top = 4.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                )
            )
        }
    }
}

@Composable
fun DrawerButton(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColor.RedFirebrick,
            contentColor = AppColor.BlackSoot
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.padding(end = 8.dp),
            tint = AppColor.BlackSoot
        )
        Text(
            text = text,
            color = AppColor.BlackSoot,
            style = TextStyle(fontSize = 20.sp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        toDonationCenters = {},
        toDonationHistory = {},
        toVerifyEligibility = {},
        toChatLive = {},
        toPdfDocument = {},
        toSchedules = {},
        logout = {}
    )
}