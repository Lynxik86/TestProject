package com.example.android_dev.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.network.RetrofitClient

class ChuckViewModel(application: Application) : AndroidViewModel(application) {

    internal val allChucks: LiveData<List<ChuckResult>> =
        MyTestDb.getDatabase(application).chuckResultDao().readAllData()

    suspend fun getChuck(applicationContext: Context): ChuckResult {
        val chuckResult = RetrofitClient.getChuckApi().randomChuck()

        MyTestDb.getDatabase(applicationContext).chuckResultDao()
            .addRandomChuck(chuckResult)
        //переключает контекст текущей сопрограммы;
        // при выполнении данного блока сопрограмма возвращается в предыдущий контекст
        Log.i("JOKE", chuckResult.id)
        return chuckResult
    }
}