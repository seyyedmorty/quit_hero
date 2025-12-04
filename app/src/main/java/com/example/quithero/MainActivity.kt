package com.example.quithero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.quithero.navigation.AppNavGraph
import com.example.quithero.navigation.BottomBar
import com.example.quithero.navigation.FAButton
import com.example.quithero.navigation.TopBar
import com.example.quithero.ui.theme.QuitHeroTheme
import com.example.quithero.viewmodel.ThemeViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDark by themeViewModel.isDarkMode.collectAsState()

            val navController = rememberNavController()

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                QuitHeroTheme(darkTheme = isDark) {   // تم اپ
                    Scaffold(
                        bottomBar = {
                            BottomBar(navController) // بعداً تعریفش می‌کنیم
                        },
                        topBar = {
                            TopBar(navController)
                        }
                    ) { paddingValues ->

                        AppNavGraph(
                            navController = navController,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                    FAButton(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)   // اینجا OK
                            .offset(y = (-95).dp),
                    )
                }
            }
        }
    }
}


