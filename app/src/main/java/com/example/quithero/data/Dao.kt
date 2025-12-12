package com.example.quithero.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SmokeInfoDao {
    @Query("SELECT * FROM smoke_info WHERE id = 0")
    fun getLastSmoke(): Flow<SmokeInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSmokeInfo(smokeInfo: SmokeInfo)
}

@Dao
interface RecordsDao {
    @Query("SELECT * FROM records ORDER BY days DESC")
    fun getAllRecords(): Flow<List<Records>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: Records)

    @Query("SELECT * FROM records WHERE days = (SELECT MAX(days) FROM records) LIMIT 1")
    suspend fun getBestRecord(): Records?
}

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile WHERE id = 0")
    fun getProfile(): Flow<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    @Query("SELECT quitReason FROM profile")
    suspend fun getQuitReason(): String

    @Query("UPDATE profile SET quitReason = :newReason WHERE id = 0")
    suspend fun updateQuitReason(newReason: String)

    @Update
    suspend fun updateProfile(profile: Profile)
}