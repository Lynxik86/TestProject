package com.example.android_dev.data.local.localdatasource

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android_dev.data.local.dao.JokeDao
import com.example.android_dev.data.model.JokeResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class JokeLocalDataSource
@Inject constructor(
    private val jokeDao: JokeDao
) {

    fun readJokeResult(): Flow<List<JokeResult>> {
        return jokeDao.readAllData()
    }

    suspend fun postJoke(jokeResult: JokeResult): JokeResult {
        jokeDao.addRandomJoke(jokeResult)
        //Send an INFO log message.
        Log.i("JOKE", jokeResult.joke)

        return jokeResult
    }

    suspend fun deleteJokes() {
        jokeDao.deleteAll()
    }

    suspend fun deleteJoke(joke: String) {

        jokeDao.deleteJokeResult(joke)

    }
}