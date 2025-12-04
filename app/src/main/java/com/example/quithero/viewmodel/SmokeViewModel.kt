package com.example.quithero.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quithero.QuitHeroApp
import com.example.quithero.data.AppDataBase
import com.example.quithero.data.SmokeInfo
import kotlinx.coroutines.launch

class SmokeViewModel(application: Application): AndroidViewModel(application) {

    private val dao = (application as QuitHeroApp).database.smokeInfoDao()

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
            dao.getLastSmoke().collect { info ->
                _smokeInfo.value = info
                _daysWithoutSmoking.value = info?.let { calculateDays(it.lastSmokeTime) } ?: 0
            }
        }
    }

    fun addSmokeInfo(date: Long) {
        viewModelScope.launch {
            val newInfo = SmokeInfo(id = 0, lastSmokeTime = date)
            dao.insertSmokeInfo(newInfo)
            _smokeInfo.value = newInfo
            _daysWithoutSmoking.value = calculateDays(date)
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
