package com.example.quithero.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quithero.QuitHeroApp
import com.example.quithero.data.Records
import com.example.quithero.data.SmokeInfo
import kotlinx.coroutines.launch

class SmokeViewModel(application: Application): AndroidViewModel(application) {

    private val smokeDao = (application as QuitHeroApp).database.smokeInfoDao()
    private val recordsDao = (application as QuitHeroApp).database.recordsDao()

    private val _smokeRecordInfo = mutableStateOf<Records?>(null)
    val smokeRecordInfo: State<Records?> = _smokeRecordInfo

    private val _smokeInfo = mutableStateOf<SmokeInfo?>(null)
    val smokeInfo: State<SmokeInfo?> = _smokeInfo

    private val _daysWithoutSmoking = mutableStateOf(0)
    val daysWithoutSmoking: State<Int> = _daysWithoutSmoking

    init {
        viewModelScope.launch {
            loadSmokeInfo()
        }
    }

    fun loadSmokeInfo(){
        viewModelScope.launch {
            smokeDao.getLastSmoke().collect { info ->
                _smokeInfo.value = info
                _daysWithoutSmoking.value = info?.let { calculateDays(it.lastSmokeTime) } ?: 0
            }
        }
        viewModelScope.launch {
            val bestRecord = recordsDao.getBestRecord()
            _smokeRecordInfo.value = bestRecord
        }
    }


    fun addSmokeInfo(date: Long, reason: String) {
        viewModelScope.launch {
            val newInfo = SmokeInfo(id = 0, lastSmokeTime = date, lastReason = reason)
            smokeDao.insertSmokeInfo(newInfo)
            _smokeInfo.value = newInfo
            _daysWithoutSmoking.value = calculateDays(date)
        }
    }

    fun addRecord(days: Int, reason: String) {
        viewModelScope.launch {
            val newRecord = Records(days = days, reason = reason)
            recordsDao.insertRecord(newRecord)
            getBestRecord()
        }
    }

    fun getBestRecord(){
        viewModelScope.launch {
            _smokeRecordInfo.value = recordsDao.getBestRecord()
        }
    }


    private fun calculateDays(lastDate: Long?): Int {
        return lastDate?.let {
            val now = System.currentTimeMillis()
            ((now - lastDate) / (1000 * 60 * 60 * 24)).toInt()
        } ?: 0
    }

    fun getTimeSinceLastSmoke(lastSmokeTime: Long): String {
        val now = System.currentTimeMillis()
        var diff = now - lastSmokeTime // میلی‌ثانیه

        val seconds = (diff / 1000) % 60
        val minutes = (diff / (1000 * 60)) % 60
        val hours = (diff / (1000 * 60 * 60)) % 24
        val days = diff / (1000 * 60 * 60 * 24)

        return "${days} روز ${hours} ساعت ${minutes} دقیقه ${seconds} ثانیه"
    }
}
