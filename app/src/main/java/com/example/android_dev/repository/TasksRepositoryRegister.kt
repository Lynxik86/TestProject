package com.example.android_dev.repository

import com.example.android_dev.db.dao.RegistryDao
import com.example.android_dev.model.FormResult

class TasksRepositoryRegister(
    private val registryDao: RegistryDao,
) : DataSourceForm {

    override fun getFormResultByLogin(firstname: String): FormResult? {

        val formResult = registryDao.getFormByLogin(firstname)
        return formResult

    }


    override fun addFormResult(formResult: FormResult) {

        registryDao.addRegistryData(formResult)

    }


}