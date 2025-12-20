package com.example.quithero.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quithero.QuitHeroApp
import com.example.quithero.data.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class OnBoardingViewModel(application: Application) : AndroidViewModel(application) {
    private val profileDao = (application as QuitHeroApp).database.profileDao()

    private val _currentStep = MutableStateFlow(0)
    val currentStep = _currentStep.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _pricePerPack = MutableStateFlow("")
    val pricePerPack = _pricePerPack.asStateFlow()

    private val _cigarettesPerDay = MutableStateFlow("")
    val cigarettesPerDay = _cigarettesPerDay.asStateFlow()

    private val _quitReason = MutableStateFlow("")
    val quitReason = _quitReason.asStateFlow()

    private val _isOnboardingVisible = MutableStateFlow<Boolean?>(null)
    val isOnboardingVisible = _isOnboardingVisible.asStateFlow()

    init {
        viewModelScope.launch {
            val profile = profileDao.getProfile().firstOrNull()
            if (profile != null && profile.isReasonAsked) {
                _isOnboardingVisible.value = false
            } else {
                profile?.let {
                    _name.value = it.name
                    _pricePerPack.value = if (it.pricePerPack != 0f) it.pricePerPack.toString() else ""
                    _cigarettesPerDay.value = if (it.cigarettesPerDay != 0) it.cigarettesPerDay.toString() else ""
                    _quitReason.value = it.quitReason
                }
                _isOnboardingVisible.value = true
            }
        }
    }

    fun onNameChange(new: String) {
        _name.value = new
    }

    fun onPriceChange(new: String) {
        val filtered = new.filter { it.isDigit() || it == '.' || it == ',' }
        _pricePerPack.value = filtered.replace(',', '.')
    }

    fun onCigarettesPerDayChange(new: String) {
        _cigarettesPerDay.value = new.filter { it.isDigit() }
    }

    fun onQuitReasonChange(new: String) {
        _quitReason.value = new
    }

    fun nextStep() {
        viewModelScope.launch {
            val currentProfile = profileDao.getProfile().firstOrNull() ?: Profile()

            when (_currentStep.value) {
                0 -> {
                    profileDao.insertProfile(currentProfile.copy(name = _name.value))
                    _currentStep.value = 1
                }
                1 -> {
                    val price = _pricePerPack.value.toFloatOrNull() ?: 0f
                    profileDao.insertProfile(currentProfile.copy(pricePerPack = price))
                    _currentStep.value = 2
                }
                2 -> {
                    val cpd = _cigarettesPerDay.value.toIntOrNull() ?: 0
                    profileDao.insertProfile(currentProfile.copy(cigarettesPerDay = cpd))
                    _currentStep.value = 3
                }
                3 -> {
                    profileDao.insertProfile(currentProfile.copy(quitReason = _quitReason.value))
                    _currentStep.value = 4
                }
            }
        }
    }

    fun prevStep() {
        _currentStep.value = (_currentStep.value - 1).coerceAtLeast(0)
    }

    fun finish(onFinished: () -> Unit) {
        viewModelScope.launch {
            val profile = profileDao.getProfile().firstOrNull() ?: Profile()
            profileDao.insertProfile(
                profile.copy(
                    name = _name.value,
                    pricePerPack = _pricePerPack.value.toFloatOrNull() ?: 0f,
                    cigarettesPerDay = _cigarettesPerDay.value.toIntOrNull() ?: 0,
                    quitReason = _quitReason.value,
                    isReasonAsked = true
                )
            )
            _isOnboardingVisible.value = false
            onFinished()
        }
    }
}
