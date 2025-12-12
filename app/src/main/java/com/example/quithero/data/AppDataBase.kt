package com.example.quithero.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SmokeInfo::class, Records::class, Profile::class ], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun smokeInfoDao(): SmokeInfoDao
    abstract fun recordsDao(): RecordsDao
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase = INSTANCE ?: synchronized(this) {
            INSTANCE?: Room.databaseBuilder(context, AppDataBase::class.java, "quit_hero_db")
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
        }
    }
}
