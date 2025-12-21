package com.example.quithero.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quithero.QuitHeroApp
import com.example.quithero.data.Profile
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val profileDao = (application as QuitHeroApp).database.profileDao()

    // تبدیل فلو دیتابیس به StateFlow برای استفاده در Compose
    val profileState: StateFlow<Profile?> = profileDao.getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun updateProfile(profile: Profile){
        viewModelScope.launch {
            profileDao.updateProfile(profile)
        }
    }
}