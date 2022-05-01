package com.example.android_dev.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.model.JokeResult
import com.example.android_dev.repository.TasksRepositoryJoke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JokeViewModel(application: Application) : AndroidViewModel(application) {


    /*private val tasksRepositoryJoke: TasksRepositoryJoke =
        TasksRepositoryJoke(MyTestDb.getDatabase(application))*/

    private val tasksRepositoryJoke: TasksRepositoryJoke =
        TasksRepositoryJoke.getInstance(MyTestDb.getDatabase(application))

    internal val allJokes: LiveData<List<JokeResult>> =
        tasksRepositoryJoke.readAllDataJokes()

    internal var allJokesDelete = MutableLiveData<String>()

    var _jokeId = MutableLiveData<String>()


    fun coroutineGetJoke() = viewModelScope.launch(Dispatchers.Default) {
        _jokeId.postValue(tasksRepositoryJoke.getJokes().joke)
    }

    fun coroutineDeleteJokes() = viewModelScope.launch(Dispatchers.Default) {
        allJokesDelete.postValue(tasksRepositoryJoke.deleteAllJokes().toString())
    }
}



