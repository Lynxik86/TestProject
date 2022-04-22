package com.example.android_dev.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android_dev.db.dao.ChuckDao
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.network.interfaces.ChuckApi

class TasksRepository(
    private val chuckDao: ChuckDao,
    private val chuckApi: ChuckApi
) {

    fun readAllData(): LiveData<List<ChuckResult>> {
        return chuckDao.readAllData()
    }

    internal suspend fun getChuck(): ChuckResult {
        val chuckResult = chuckApi.randomChuck()

        chuckDao.addRandomChuck(chuckResult)
        //переключает контекст текущей сопрограммы;
        // при выполнении данного блока сопрограмма возвращается в предыдущий контекст
        Log.i("JOKE", chuckResult.id)
        return chuckResult
    }
}