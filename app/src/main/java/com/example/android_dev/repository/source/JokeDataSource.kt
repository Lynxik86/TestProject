package com.example.android_dev.repository.source

import androidx.lifecycle.LiveData
import com.example.android_dev.data.model.JokeResult

interface JokeDataSource {

    fun readAllDataJokes(): LiveData<List<JokeResult>>

    suspend fun getJokes(): JokeResult

    suspend fun deleteAllJokes()

    suspend fun deleteJokeResult(joke: String)

}