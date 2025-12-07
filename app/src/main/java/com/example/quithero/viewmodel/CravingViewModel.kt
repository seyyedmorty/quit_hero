package com.example.quithero.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quithero.data.CravingModes
import com.example.quithero.data.CravingModesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.BufferedReader

class CravingViewModel(private val repository: CravingRepository): ViewModel() {

    private val _moods = MutableStateFlow<List<CravingModes>> (emptyList())
    val moods: StateFlow<List<CravingModes>> = _moods

    private val _selectedMode = MutableStateFlow<CravingModes?>(null)
    val selectedMode: StateFlow<CravingModes?> = _selectedMode

    fun loadModes(){
        viewModelScope.launch {
            val data = repository.loadFromAssets()
            _moods.value = data
        }
    }

    fun selectMode(mode: CravingModes){
        _selectedMode.value = mode
    }
}

class CravingRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    fun loadFromAssets(): List<CravingModes> {
        val text = context.assets.open("craving_modes.json")
            .reader()
            .readText()
        val response = Json { ignoreUnknownKeys = true }
            .decodeFromString<CravingModesResponse>(text)
        return response.craving_modes
    }
}