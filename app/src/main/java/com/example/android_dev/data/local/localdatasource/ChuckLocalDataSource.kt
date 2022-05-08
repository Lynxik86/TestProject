package com.example.android_dev.data.local.localdatasource

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android_dev.data.local.dao.ChuckDao
import com.example.android_dev.data.model.ChuckResult
import javax.inject.Inject

class ChuckLocalDataSource @Inject constructor(
    private val chuckDao: ChuckDao
)
{
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