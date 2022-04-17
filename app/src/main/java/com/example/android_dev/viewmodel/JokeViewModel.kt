package com.example.android_dev.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.model.JokeResult
import com.example.android_dev.network.RetrofitClient

class JokeViewModel(application: Application) : AndroidViewModel(application) {

    internal val allJokes: LiveData<List<JokeResult>> =
        MyTestDb.getDatabase(application).jokeResultDao().readAllData()

    suspend fun getJokes(applicationContext: Context): JokeResult {

        val jokeResult = RetrofitClient.getJokeApi().randomJoke()
        MyTestDb.getDatabase(applicationContext).jokeResultDao()
            .addRandomJoke(jokeResult)
        //Send an INFO log message.
        Log.i("JOKE", jokeResult.joke)

        return jokeResult
    }
}