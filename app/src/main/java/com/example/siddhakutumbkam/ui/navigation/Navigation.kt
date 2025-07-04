package com.example.siddhakutumbkam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.siddhakutumbkam.ui.screens.DiscourseScreen
import com.example.siddhakutumbkam.ui.screens.HomeScreen
import com.example.siddhakutumbkam.ui.screens.RetreatBookingScreen
import com.example.siddhakutumbkam.ui.screens.RetreatDetailScreen
import com.example.siddhakutumbkam.ui.screens.RetreatListScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Discourse : Screen("discourse")
    object RetreatList : Screen("retreat_list")
    object RetreatDetail : Screen("retreat_detail/{retreatId}") {
        fun createRoute(retreatId: String) = "retreat_detail/$retreatId"
    }
    object RetreatBooking : Screen("retreat_booking/{retreatId}") {
        fun createRoute(retreatId: String) = "retreat_booking/$retreatId"
    }
}

@Composable
fun SiddhaKutumbkamNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDiscourse = {
                    navController.navigate(Screen.Discourse.route)
                },
                onNavigateToRetreats = {
                    navController.navigate(Screen.RetreatList.route)
                }
            )
        }
        
        composable(Screen.Discourse.route) {
            DiscourseScreen()
        }
        
        composable(Screen.RetreatList.route) {
            RetreatListScreen(
                onRetreatClick = { retreatId ->
                    navController.navigate(Screen.RetreatDetail.createRoute(retreatId))
                }
            )
        }
        
        composable(Screen.RetreatDetail.route) { backStackEntry ->
            val retreatId = backStackEntry.arguments?.getString("retreatId") ?: ""
            RetreatDetailScreen(
                retreatId = retreatId,
                onBookRetreat = { id ->
                    navController.navigate(Screen.RetreatBooking.createRoute(id))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.RetreatBooking.route) { backStackEntry ->
            val retreatId = backStackEntry.arguments?.getString("retreatId") ?: ""
            RetreatBookingScreen(
                retreatId = retreatId,
                onBookingComplete = {
                    navController.popBackStack(Screen.RetreatList.route, false)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
