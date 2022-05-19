package com.example.android_dev.repository.source

import androidx.lifecycle.LiveData
import com.example.android_dev.data.model.ChuckResult
import kotlinx.coroutines.flow.Flow

interface ChuckDataSource {

    fun readAllDataChuck(): Flow<List<ChuckResult>>
    suspend fun getChuck(): ChuckResult
    suspend fun deleteAllChucks()
    suspend fun deleteChuckResult(chuck:String)

}