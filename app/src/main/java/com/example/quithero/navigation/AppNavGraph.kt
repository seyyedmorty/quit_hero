package com.example.quithero.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quithero.ui.screens.HomeScreen
import com.example.quithero.ui.screens.MotivationScreen
import com.example.quithero.ui.screens.ProfileScreen
import com.example.quithero.ui.screens.SettingScreen

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Routes.SETTING,
        modifier = modifier
    ) {
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.SETTING) { SettingScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }
        composable(Routes.MOTIVATION) { MotivationScreen(navController) }
    }
}