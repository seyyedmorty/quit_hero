package com.example.quithero

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.quithero.navigation.AppNavGraph
import com.example.quithero.navigation.BottomBar
import com.example.quithero.navigation.FAButton
import com.example.quithero.navigation.TopBar
import com.example.quithero.ui.screens.OnboardingScreen
import com.example.quithero.ui.theme.QuitHeroTheme
import com.example.quithero.viewmodel.OnBoardingViewModel
import com.example.quithero.viewmodel.StartSoundViewModel
import com.example.quithero.viewmodel.ThemeViewModel
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val onboardingVM: OnBoardingViewModel = viewModel()
            val isOnboardingVisible by onboardingVM.isOnboardingVisible.collectAsState()
            var showMainScreen by remember { mutableStateOf(false) }

            if (isOnboardingVisible == null) {
                Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background))
                return@setContent
            }

            if (/*false*/ isOnboardingVisible == true && !showMainScreen) {
                QuitHeroTheme() {
                    OnboardingScreen(viewModel = onboardingVM) {
                        showMainScreen = true
                    }
                }
            } else {
                // UI اصلی اپ: Scaffold + NavGraph + BottomBar + TopBar
                val ctx = LocalContext.current
                val startSoundVM: StartSoundViewModel = viewModel()
                val isEnabled by startSoundVM.isStartSoundEnabled.collectAsState()

                if (isEnabled) {
                    LaunchedEffect(Unit) {
                        val mediaPlayer = MediaPlayer.create(ctx, R.raw.intro_sound)
                        mediaPlayer.start()
                        mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
                    }
                }

                val themeViewModel: ThemeViewModel = viewModel()
                val isDark by themeViewModel.isDarkMode.collectAsState()
                val navController = rememberNavController()

                QuitHeroTheme(darkTheme = isDark) {
                    Scaffold(
                        bottomBar = { BottomBar(navController) },
                        topBar = { TopBar(navController) },
                        floatingActionButton = { FAButton(modifier = Modifier.offset(y = 30.dp)) },
                        floatingActionButtonPosition = FabPosition.Center
                    ) { paddingValues ->
                        AppNavGraph(
                            navController = navController,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }


        }
    }
}


