package com.example.quithero.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import okhttp3.Route

@Composable
fun BottomBar(nav: NavController) {

    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val listOfItems = listOf<BottomNavItem>(
        BottomNavItem.Home, BottomNavItem.Motivation, BottomNavItem.Profile, BottomNavItem.Setting
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {

        listOfItems.forEach { item ->

            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute?.startsWith(item.route) == true) {
                        nav.popBackStack(item.route, inclusive = false)
                    }
                    else {
                        nav.navigate(item.route) {
                            popUpTo(nav.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {  Box(
                    modifier = Modifier
                        .size(if (selected) 40.dp else 28.dp),  // بزرگ‌تر شدن محل آیکون وقتی انتخاب شد
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                } },
                colors = NavigationBarItemDefaults.colors(
//                  selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
            )
        }
    }
}


sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    object Home : BottomNavItem(Routes.HOME, "", Icons.Filled.Dashboard)
    object Motivation : BottomNavItem(Routes.MOTIVATION, "", Icons.Filled.Terrain)
    object Profile : BottomNavItem(Routes.PROFILE, "", Icons.Filled.AccountCircle)
    object Setting : BottomNavItem(Routes.SETTING, "", Icons.Filled.Settings)
}