package com.example.android_dev.data.local.localdatasource

import com.example.android_dev.data.local.dao.RegistryDao
import com.example.android_dev.data.model.FormResult
import javax.inject.Inject

class FormLocalDataSource @Inject constructor(private val formResultRegistryDao: RegistryDao) {


    fun getFormByLogin(firstname: String): FormResult? {
        val formResult = formResultRegistryDao.getFormByLogin(firstname)
        return formResult
    }


    fun addRegistryFormData(formResult: FormResult) {
        formResultRegistryDao.addRegistryData(formResult)
    }

    suspend fun deleteFormResultNotAdmin() {
        formResultRegistryDao.deleteAllNotAdmin()


    }
}