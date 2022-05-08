package com.example.android_dev.repository.source

import com.example.android_dev.data.model.FormResult

interface FormDataSource {

    fun getFormResultByLogin(firstname: String): FormResult?

    fun addFormResult(formResult: FormResult)

    suspend fun deleteAllNotAdmin()

}