package com.example.android_dev.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_dev.data.model.JokeResult
import com.example.android_dev.repository.JokeTasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    application: Application,
    private val jokeTasksRepository: JokeTasksRepository
) : AndroidViewModel(application) {
    /* private val  jokeTasksRepository: JokeTasksRepository =
         JokeTasksRepository(MyTestDb.getDatabase(application))*/

    /* private val jokeTasksRepository: JokeTasksRepository =
         JokeTasksRepository.getInstance(ConnectDb.getDatabase(application))*/

    internal val _jokeId = MutableStateFlow("")

    fun coroutineGetJoke() = viewModelScope.launch(Dispatchers.Default) {
        _jokeId.emit(jokeTasksRepository.getJokes().joke)
    }

    val allJokes: Flow<List<JokeResult>> = jokeTasksRepository.readAllDataJokes()

    private var jokeResultDelete = MutableStateFlow("")

    fun coroutineDeleteJokeResult(joke: String) = viewModelScope.launch(Dispatchers.Default) {
        jokeResultDelete.emit(jokeTasksRepository.deleteJokeResult(joke).toString())
    }

    private var allJokesDelete = MutableStateFlow("")
    fun coroutineDeleteJokes() = viewModelScope.launch(Dispatchers.Default) {
        allJokesDelete.emit(jokeTasksRepository.deleteAllJokes().toString())
    }

    /*
    internal val allJokes: LiveData<List<JokeResult>> =
        jokeTasksRepository.readAllDataJokes()

    internal var _jokeId = MutableLiveData<String>()
    fun coroutineGetJoke() = viewModelScope.launch(Dispatchers.Default) {
        _jokeId.postValue(jokeTasksRepository.getJokes().joke)
    }

    private var allJokesDelete = MutableLiveData<String>()

    fun coroutineDeleteJokes() = viewModelScope.launch(Dispatchers.Default) {
        allJokesDelete.postValue(jokeTasksRepository.deleteAllJokes().toString())
    }

    private var jokeResultDelete = MutableLiveData<String>()

    fun coroutineDeleteJokeResult(joke:String) = viewModelScope.launch(Dispatchers.Default) {
        jokeResultDelete.postValue(jokeTasksRepository.deleteJokeResult(joke).toString())
    }
    */
}





