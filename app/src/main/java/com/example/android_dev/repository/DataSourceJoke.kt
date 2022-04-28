package com.example.android_dev.repository

import androidx.lifecycle.LiveData
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.model.FormResult
import com.example.android_dev.model.JokeResult

interface DataSourceJoke {

    fun readAllDataJokes(): LiveData<List<JokeResult>>

    suspend fun getJokes(): JokeResult

}