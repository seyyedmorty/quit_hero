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