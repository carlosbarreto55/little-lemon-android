package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.data.MenuItemDao
import com.example.littlelemon.data.UserPreferences

object Destinations {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val PROFILE = "profile"
}

@Composable
fun AppNavigation(navController: NavHostController, prefs: UserPreferences, dao: MenuItemDao) {
    val start = if (prefs.isLoggedIn) Destinations.HOME else Destinations.ONBOARDING

    NavHost(navController = navController, startDestination = start) {
        composable(Destinations.ONBOARDING) {
            Onboarding(prefs) {
                navController.navigate(Destinations.HOME) {
                    popUpTo(Destinations.ONBOARDING) { inclusive = true }
                }
            }
        }
        composable(Destinations.HOME) {
            Home(dao) { navController.navigate(Destinations.PROFILE) }
        }
        composable(Destinations.PROFILE) {
            Profile(prefs) {
                navController.navigate(Destinations.ONBOARDING) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }
}
