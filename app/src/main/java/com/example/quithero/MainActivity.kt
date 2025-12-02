package com.example.quithero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.quithero.navigation.AppNavGraph
import com.example.quithero.ui.theme.QuitHeroTheme
import com.example.quithero.viewmodel.ThemeViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewMode: ThemeViewModel = viewModel()
            val isDark by themeViewMode.isDarkMode.collectAsState()


            val navController = rememberNavController()
            QuitHeroTheme(darkTheme = isDark) {
                AppNavGraph(navController)
            }
        }
    }
}


