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
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.repository.TasksRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JokeViewModel(application: Application) : AndroidViewModel(application) {


    private val tasksRepository: TasksRepository =
        TasksRepository(MyTestDb.getDatabase(application).chuckResultDao(),
            RetrofitClient.getChuckApi(), MyTestDb.getDatabase(application).jokeResultDao(),
            RetrofitClient.getJokeApi(), MyTestDb.getDatabase(application).formResultDao()
        )


    internal val allJokes: LiveData<List<JokeResult>> =
        tasksRepository.readAllDataJokes()

    var _jokeId = MutableLiveData<String>()


    fun coroutineGetJoke()= viewModelScope.launch(Dispatchers.Default) {
        _jokeId.postValue(tasksRepository.getJokes().joke)
       }
}



