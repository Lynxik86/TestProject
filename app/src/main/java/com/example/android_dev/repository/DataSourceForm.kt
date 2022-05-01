package com.example.android_dev.repository

import androidx.lifecycle.LiveData
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.model.FormResult
import com.example.android_dev.model.JokeResult

interface DataSourceForm {

    fun getFormResultByLogin(firstname: String): FormResult?

    fun addFormResult (formResult: FormResult)

    suspend fun deleteAllNotAdmin()

}