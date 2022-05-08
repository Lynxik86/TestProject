package com.example.android_dev.repository.source

import androidx.lifecycle.LiveData
import com.example.android_dev.data.model.ChuckResult

interface ChuckDataSource {

    fun readAllDataChuck(): LiveData<List<ChuckResult>>
    suspend fun getChuck(): ChuckResult
    suspend fun deleteAllChucks()

}