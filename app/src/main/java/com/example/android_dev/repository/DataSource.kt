package com.example.android_dev.repository

import androidx.lifecycle.LiveData
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.model.FormResult
import com.example.android_dev.model.JokeResult

interface DataSource {

    fun readAllDataChuck(): LiveData<List<ChuckResult>>

    fun readAllDataJokes(): LiveData<List<JokeResult>>

    suspend fun getChuck(): ChuckResult

    suspend fun getJokes(): JokeResult

    fun getFormResultByLogin(firstname: String): FormResult?

    fun addFormResult (formResult: FormResult)


}