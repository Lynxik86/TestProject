package com.example.android_dev.repository.source

import androidx.lifecycle.LiveData
import com.example.android_dev.data.model.JokeResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface JokeDataSource {

    fun readAllDataJokes(): Flow<List<JokeResult>>

    suspend fun getJokes(): JokeResult

    suspend fun deleteAllJokes()

    suspend fun deleteJokeResult(joke: String)

}