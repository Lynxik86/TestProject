package com.example.android_dev.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android_dev.db.dao.ChuckDao
import com.example.android_dev.model.ChuckResult

class LocalDataSourceChuck(
    private val chuckDao: ChuckDao
) {
    fun readChuckResult(): LiveData<List<ChuckResult>> {
        return chuckDao.readAllData()
    }

    fun postChuck(chuckResult: ChuckResult): ChuckResult {

        chuckDao.addRandomChuck(chuckResult)
        Log.i("JOKE", chuckResult.id)
        return chuckResult
    }

    suspend fun deleteChucks() {
        chuckDao.deleteAll()
    }
}