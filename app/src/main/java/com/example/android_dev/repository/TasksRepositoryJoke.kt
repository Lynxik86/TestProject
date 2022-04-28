package com.example.android_dev.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android_dev.db.dao.JokeDao
import com.example.android_dev.model.JokeResult
import com.example.android_dev.network.interfaces.JokeApi

class TasksRepositoryJoke(
    private val jokeDao: JokeDao,
    private val jokeApi: JokeApi,
) : DataSourceJoke {

    override fun readAllDataJokes(): LiveData<List<JokeResult>> {
        return jokeDao.readAllData()
    }


    override suspend fun getJokes(): JokeResult {

        val jokeResult = jokeApi.randomJoke()
        jokeDao.addRandomJoke(jokeResult)
        //Send an INFO log message.
        Log.i("JOKE", jokeResult.joke)

        return jokeResult

    }
}