package com.example.quithero.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quithero.ui.settings.DarkModeButton
import com.example.quithero.viewmodel.ThemeViewModel


@Composable
fun SettingScreen(nav: NavController) {

    val themeViewModel: ThemeViewModel = viewModel()
    val isDark by themeViewModel.isDarkMode.collectAsState()




    Scaffold() { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DarkModeButton(isDark, themeViewModel)
        }
    }
}