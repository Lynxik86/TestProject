package com.example.android_dev.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android_dev.db.dao.JokeDao
import com.example.android_dev.model.JokeResult

class LocalDataSourceJoke(
    private val jokeDao: JokeDao,
) {

    fun readJokeResult(): LiveData<List<JokeResult>> {
        return jokeDao.readAllData()
    }

    suspend fun postJoke(jokeResult: JokeResult): JokeResult {

        jokeDao.addRandomJoke(jokeResult)
        //Send an INFO log message.
        Log.i("JOKE", jokeResult.joke)

        return jokeResult
    }
}