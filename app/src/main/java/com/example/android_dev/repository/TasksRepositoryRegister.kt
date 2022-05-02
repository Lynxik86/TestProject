package com.example.android_dev.repository

import com.example.android_dev.db.LocalDataSourceForm
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.model.FormResult

class TasksRepositoryRegister private constructor(private val connectDb: MyTestDb) :
    DataSourceForm {
    companion object {
        @Volatile
        private var INSTANCE: TasksRepositoryRegister? = null

        fun getInstance(connectDb: MyTestDb): TasksRepositoryRegister =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TasksRepositoryRegister(connectDb).also { INSTANCE = it }
            }
    }

    private val localDataSourceForm: LocalDataSourceForm =
        LocalDataSourceForm(
            connectDb.formResultDao(),
        )

    override fun getFormResultByLogin(firstname: String): FormResult? {
        return localDataSourceForm.getFormByLogin(firstname)
    }

    override fun addFormResult(formResult: FormResult) {
        localDataSourceForm.addRegistryFormData(formResult)
    }

    override suspend fun deleteAllNotAdmin() {
        localDataSourceForm.deleteFormResultNotAdmin()
    }
}