package com.example.android_dev.repository

import androidx.lifecycle.LiveData
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.model.FormResult
import com.example.android_dev.model.JokeResult

interface DataSourceChuck {

    fun readAllDataChuck(): LiveData<List<ChuckResult>>
    suspend fun getChuck(): ChuckResult

}