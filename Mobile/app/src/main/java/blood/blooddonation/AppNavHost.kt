package blood.blooddonation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import blood.blooddonation.GeneralInformation.GeneralInformationScreen
import blood.blooddonation.authentication.login.LoginScreen
import blood.blooddonation.authentication.register.RegisterScreen
import blood.blooddonation.authentication.resetPassword.ResetPasswordScreen
import blood.blooddonation.chatLive.ChatLiveScreen
import blood.blooddonation.donationCenter.donationCenters.DonationCenterPage
import blood.blooddonation.donationCenter.donationCenters.DonationCentersScreen
import blood.blooddonation.donationHistory.DonationHistoryScreen
import blood.blooddonation.homeScreen.HomeScreen
import blood.blooddonation.pdfDocument.PdfDocumentScreen
import blood.blooddonation.verifyEligibility.QuestionScreen
import blood.blooddonation.preferences.UserPreferences
import blood.blooddonation.preferences.UserPreferencesViewModel
import blood.blooddonation.schedules.SchedulesScreen
import blood.blooddonation.utils.Api
import blood.blooddonation.verifyEligibility.VerifyEligibilityScreen

val loginRoute = "login"
val registerRoute = "register"
val generalInfoRoute = "generalInfoRoute"
val resetPasswordRoute = "resetPassword"
val homeRoute = "homeRoute"
val donationCentersListRoute = "donationCentersListRoute"
val donationHistoryListRoute = "donationHistoryListRoute"
val verifyEligibilityRoute = "verifyEligibilityRoute"
val nextQuestionRoute = "nextQuestionRoute"
val chatLive = "chatLive"
val pdfDocument = "pdfDocument"
val schedules = "schedules"

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val userPreferencesViewModel =
        viewModel<UserPreferencesViewModel>(factory = UserPreferencesViewModel.Factory)

    val userPreferencesUiState by userPreferencesViewModel.uiState.collectAsStateWithLifecycle(
        initialValue = UserPreferences()
    )

    val appViewModel = viewModel<AppViewModel>(factory = AppViewModel.Factory)

    NavHost(navController = navController, startDestination = loginRoute){

        composable(route = homeRoute) {
            HomeScreen(
                toDonationCenters = { userId -> navController.navigate("$donationCentersListRoute/$userId") },
                toDonationHistory = { userId -> navController.navigate("$donationHistoryListRoute/$userId") },
                toVerifyEligibility = { userId -> navController.navigate("$verifyEligibilityRoute/$userId") },
                toChatLive = { navController.navigate(chatLive) },
                toPdfDocument = { userId -> navController.navigate("$pdfDocument/$userId") },
                toSchedules = { userId -> navController.navigate("$schedules/$userId") },
                logout = { navController.navigate(loginRoute) }
            )
        }

        composable(route = loginRoute){
            LoginScreen(
                toRegister = { navController.navigate(registerRoute) },
                toResetPass = { navController.navigate(resetPasswordRoute) },
                onClose = { navController.navigate(homeRoute) }
            )
        }

        composable(route = registerRoute){
            RegisterScreen(
                toLogin = { navController.navigate(loginRoute) },
                toResetPass = { navController.navigate(resetPasswordRoute) },
                onClose = { navController.navigate(generalInfoRoute) }
            )
        }

        composable(route = generalInfoRoute){
            GeneralInformationScreen (
                onClose = { navController.navigate(homeRoute) }
            )
        }

        composable(route = resetPasswordRoute){
            ResetPasswordScreen(
                toLogin = { navController.navigate(loginRoute) },
                toRegister = { navController.navigate(registerRoute) },
                onClose = { navController.navigate(loginRoute) }
            )
        }

        composable(
            route = "$donationCentersListRoute/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ){
            DonationCentersScreen(
                userId = it.arguments?.getString("userId"),
                toCenterSelected = { center ->
                    navController.navigate("$donationCentersListRoute/${center.id}/${it.arguments?.getString("userId")}")
                },
                goBack = { navController.navigate(homeRoute) }
            )
        }

        composable(
                route = "$donationCentersListRoute/{centerId}/{userId}",
                arguments = listOf(
                    navArgument("centerId") { type = NavType.StringType },
                    navArgument("userId") { type = NavType.StringType }
                ),
        )
        {
            DonationCenterPage(
                itemId = it.arguments?.getString("centerId"),
                userId = it.arguments?.getString("userId"),
                goBack = { userId -> navController.navigate("$donationCentersListRoute/${userId}") }
            )
        }

        composable(
            route = "$donationHistoryListRoute/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        )
        {
            DonationHistoryScreen(
                userId = it.arguments?.getString("userId"),
                goBack = { navController.navigate(homeRoute) }
            )
        }

        composable(
            route = "$verifyEligibilityRoute/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) {
            VerifyEligibilityScreen(
                userId = it.arguments?.getString("userId"),
                toQuestions = { userId -> navController.navigate("$nextQuestionRoute/$userId") },
                goBack = { navController.navigate(homeRoute) }
            )
        }

        composable(
            route = "$nextQuestionRoute/{userId}" ,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) {
            QuestionScreen(
                userId = it.arguments?.getString("userId"),
                nextQuestion = { userId -> navController.navigate("$nextQuestionRoute/$userId") },
                goBack = { navController.navigate(homeRoute) }
            )
        }

        composable(route = chatLive){
            ChatLiveScreen (
                goBack = { navController.navigate(homeRoute) }
            )
        }

        composable(
            route = "$pdfDocument/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ){
            PdfDocumentScreen(
                userId = it.arguments?.getString("userId"),
                goBack = { navController.navigate(homeRoute) }
            )
        }

        composable(
            route = "$schedules/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ){
            SchedulesScreen(
                userId = it.arguments?.getString("userId"),
                goBack = { navController.navigate(homeRoute) }
            )
        }
    }

    LaunchedEffect(userPreferencesUiState.token) {
        if(userPreferencesUiState.token.isNotEmpty() && userPreferencesUiState.finish) {
            Api.tokenInterceptor.token = userPreferencesUiState.token
            appViewModel.setToken(userPreferencesUiState.token)
            navController.navigate(homeRoute) {
                popUpTo(0)
            }
        }
    }
}