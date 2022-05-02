package com.example.android_dev.db

import com.example.android_dev.db.dao.RegistryDao
import com.example.android_dev.model.FormResult

class LocalDataSourceForm(private val formResultDao: RegistryDao) {


    fun getFormByLogin(firstname: String): FormResult? {
        val formResult = formResultDao.getFormByLogin(firstname)
        return formResult
    }


    fun addRegistryFormData(formResult: FormResult) {
        formResultDao.addRegistryData(formResult)
    }

    suspend fun deleteFormResultNotAdmin() {
        formResultDao.deleteAllNotAdmin()


    }
}