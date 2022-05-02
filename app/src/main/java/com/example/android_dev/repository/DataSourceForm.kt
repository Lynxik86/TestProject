package com.example.android_dev.repository

import com.example.android_dev.model.FormResult

interface DataSourceForm {

    fun getFormResultByLogin(firstname: String): FormResult?

    fun addFormResult(formResult: FormResult)

    suspend fun deleteAllNotAdmin()

}