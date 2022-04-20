package com.example.android_dev.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import com.example.android_dev.db.MyTestDb

import com.example.android_dev.model.JokeResult
import com.example.android_dev.network.RetrofitClient
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch

class JokeViewModel(application: Application) : AndroidViewModel(application) {

    internal val allJokes: LiveData<List<JokeResult>> =
        MyTestDb.getDatabase(application).jokeResultDao().readAllData()

    var _jokeId = MutableLiveData<String>()

    private suspend fun getJokes(applicationContext: Context): JokeResult {

        val jokeResult = RetrofitClient.getJokeApi().randomJoke()
        MyTestDb.getDatabase(applicationContext).jokeResultDao()
            .addRandomJoke(jokeResult)
        //Send an INFO log message.
        Log.i("JOKE", jokeResult.joke)

        return jokeResult

    }

    fun coroutineGetJoke(applicationContext: Context)= viewModelScope.launch {
        _jokeId.postValue(getJokes(applicationContext).joke)
       }
}



