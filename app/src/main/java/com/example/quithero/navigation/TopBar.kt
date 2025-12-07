package com.example.quithero.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(nav: NavController) {

    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title = when (currentRoute) {
        Routes.HOME -> "داشبورد"
        Routes.MOTIVATION -> "بر فراز قله‌ها"
        Routes.PROFILE -> "پروفایل"
        Routes.SETTING -> "تنظیمات"
        Routes.BENEFITS -> "فواید ترک سیگار"
        Routes.TEMPTATION -> "تکنیک‌های مقابله"
        else -> "QuitHero"
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        navigationIcon = {
            if (currentRoute == Routes.TEMPTATION || currentRoute == Routes.BENEFITS) {
                androidx.compose.material3.IconButton(
                    onClick = { nav.popBackStack() } // برگشت به صفحه قبل
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "بازگشت",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )

}