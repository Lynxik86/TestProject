package com.example.android_dev.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_dev.data.local.ConnectDb
import com.example.android_dev.data.model.JokeResult
import com.example.android_dev.repository.JokeTasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor (
    application: Application,
    private val  jokeTasksRepository: JokeTasksRepository
    ) : AndroidViewModel(application) {


   /* private val  jokeTasksRepository: JokeTasksRepository =
        JokeTasksRepository(MyTestDb.getDatabase(application))*/

   /* private val jokeTasksRepository: JokeTasksRepository =
        JokeTasksRepository.getInstance(ConnectDb.getDatabase(application))*/

    internal val allJokes: LiveData<List<JokeResult>> =
        jokeTasksRepository.readAllDataJokes()

    private var allJokesDelete = MutableLiveData<String>()

    var _jokeId = MutableLiveData<String>()


    fun coroutineGetJoke() = viewModelScope.launch(Dispatchers.Default) {
        _jokeId.postValue(jokeTasksRepository.getJokes().joke)
    }

    fun coroutineDeleteJokes() = viewModelScope.launch(Dispatchers.Default) {
        allJokesDelete.postValue(jokeTasksRepository.deleteAllJokes().toString())
    }
}



