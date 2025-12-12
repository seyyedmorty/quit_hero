package com.example.quithero.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "smoke_info")
data class SmokeInfo(
    @PrimaryKey val id: Int = 0,
    val lastSmokeTime: Long,
    val lastReason: String,
)

@Entity(tableName = "records")
data class Records(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val days: Int,
    val reason: String,
)

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey val id: Int = 0,
    val name: String = "",
    val age: Int = 0,
    val cigarettesPerDay: Int = 0,
    val pricePerPack: Float = 0f,
    val quitReason: String = "",
    val isReasonAsked: Boolean = false,
)

@Serializable
data class CravingModes(
    val mode: String,
    val techniques: List<String>,
)

@Serializable
data class CravingModesResponse(
    val craving_modes: List<CravingModes>,
)