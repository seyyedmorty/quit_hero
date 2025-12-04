package com.example.quithero.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val DATASTORE_NAME = "user_preferences"
val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

val START_SOUND_KEY = booleanPreferencesKey("start_sound_enabled")