package com.example.quithero.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary = DarkOrange,
    secondary = DarkAccent,
    tertiary = DarkOrange,
    background = DarkGray,
    surface = Color(0xFF3F3E3E),
    onSurface = Color(0xFFC7C7C7),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
)

val LightColorScheme = lightColorScheme(
    primary = LightOrange,
    secondary = LightAccent,
    tertiary = LightOrange,
    background = Color(0xFFC7C7C7),
    surface = Color(0xFFE0E0E0),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun QuitHeroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}