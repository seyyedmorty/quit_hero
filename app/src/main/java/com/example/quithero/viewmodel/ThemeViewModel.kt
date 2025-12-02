package com.example.quithero.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quithero.data.ThemePreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ThemePreferencesRepository = ThemePreferencesRepository(application)

    val isDarkMode: StateFlow<Boolean> = repository.isDarkModeFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun setDarkMode(isDark: Boolean){
        viewModelScope.launch {
            repository.setDarkMode(isDark)
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val currentMode = isDarkMode.value
            repository.setDarkMode(!currentMode)
        }
    }
}