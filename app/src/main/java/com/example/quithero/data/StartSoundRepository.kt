package com.example.quithero.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StartSoundRepository(private val context: Context) {

    val isStartSoundEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[START_SOUND_KEY] ?: true  // پیش‌فرض روشن
        }

    suspend fun setStartSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[START_SOUND_KEY] = enabled
        }
    }
}