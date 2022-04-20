package com.example.android_dev.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.model.FormResult

class RegistryViewModel(application: Application) : AndroidViewModel(application) {

    fun writeRegistryData(
        applicationContext: Context,
        firstname: String,
        lastname: String,
        email: String,
        phone: String
    ) {
        val formResult =
            FormResult(first_name = firstname, last_name = lastname, email = email, phone = phone)
        MyTestDb.getDatabase(applicationContext).formResultDao().addRegistryData(formResult)

    }
}