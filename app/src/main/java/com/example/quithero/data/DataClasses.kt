package com.example.quithero.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "smoke_info")
data class SmokeInfo(
    @PrimaryKey val id: Int = 0,
    val lastSmokeTime: Long,
)