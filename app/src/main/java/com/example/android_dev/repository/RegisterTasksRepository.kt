package com.example.android_dev.repository

import com.example.android_dev.data.local.localdatasource.FormLocalDataSource
import com.example.android_dev.data.local.ConnectDb
import com.example.android_dev.data.model.FormResult
import com.example.android_dev.repository.source.FormDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterTasksRepository @Inject constructor( val connectDb: ConnectDb) :
    FormDataSource {
   /* companion object {
        @Volatile
        private var INSTANCE: RegisterTasksRepository? = null

        fun getInstance(connectDb: ConnectDb): RegisterTasksRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RegisterTasksRepository(connectDb).also { INSTANCE = it }
            }
    }*/

    private val formLocalDataSource: FormLocalDataSource =
        FormLocalDataSource(
            connectDb.registryDao(),
        )

    override fun getFormResultByLogin(firstname: String): FormResult? {
        return formLocalDataSource.getFormByLogin(firstname)
    }

    override fun addFormResult(formResult: FormResult) {
        formLocalDataSource.addRegistryFormData(formResult)
    }

    override suspend fun deleteAllNotAdmin() {
        formLocalDataSource.deleteFormResultNotAdmin()
    }
}