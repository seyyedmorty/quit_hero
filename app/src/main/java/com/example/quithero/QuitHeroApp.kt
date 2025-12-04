package com.example.quithero

import android.app.Application
import com.example.quithero.data.AppDataBase

class QuitHeroApp : Application() {
    val database by lazy {
        AppDataBase.getDatabase(this)
    }
}