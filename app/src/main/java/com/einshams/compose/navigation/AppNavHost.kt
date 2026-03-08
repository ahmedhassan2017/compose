package com.einshams.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.einshams.compose.ui.details.DetailsRoute
import com.einshams.compose.ui.home.HomeRoute
import com.einshams.compose.ui.login.LoginRoute
import com.einshams.compose.ui.splash.SplashRoute

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        composable(Routes.Splash) {
            SplashRoute(
                onFinished = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Splash) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.Login) {
            LoginRoute(
                onLoggedIn = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.Home) {
            HomeRoute(
                onOpenDetails = { id -> navController.navigate(Routes.details(id)) }
            )
        }

        composable(
            route = Routes.DetailsPattern,
            arguments = listOf(navArgument(Routes.DetailsIdArg) { type = NavType.StringType })
        ) { entry ->
            val id = entry.arguments?.getString(Routes.DetailsIdArg).orEmpty()
            DetailsRoute(
                id = id,
                onBack = { navController.navigateUp() }
            )
        }
    }
}
