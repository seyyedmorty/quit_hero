package com.example.quithero.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quithero.data.StartSoundRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StartSoundViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = StartSoundRepository(application)

    val isStartSoundEnabled: StateFlow<Boolean> = repo.isStartSoundEnabled.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    fun setStartSound(enabled: Boolean) {
        viewModelScope.launch {
            repo.setStartSoundEnabled(enabled)
        }
    }
}
