package com.example.android_dev.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.android_dev.data.model.JokeResult
import com.example.android_dev.repository.JokeTasksRepository
import com.example.android_dev.ui.recyclerview.RecyclerJokeAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
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


    internal val _jokeId = MutableStateFlow("")

    fun coroutineGetJoke() =viewModelScope.launch(Dispatchers.Default) {

        _jokeId.emit(jokeTasksRepository.getJokes().joke)

    }

    /*fun coroutineGetJoke() {

        _jokeId. value=jokeTasksRepository.getJokes().joke

    }*/









 /*
   internal var _jokeId = MutableLiveData<String>()

  private var allJokesDelete = MutableLiveData<String>()

 internal val allJokes: LiveData<List<JokeResult>> =
        jokeTasksRepository.readAllDataJokes()

    private var jokeResultDelete = MutableLiveData<String>()

    fun coroutineGetJoke() = viewModelScope.launch(Dispatchers.Default) {
        _jokeId.postValue(jokeTasksRepository.getJokes().joke)
    }

    fun coroutineDeleteJokes() = viewModelScope.launch(Dispatchers.Default) {
        allJokesDelete.postValue(jokeTasksRepository.deleteAllJokes().toString())
    }

    fun coroutineDeleteJokeResult(joke:String) = viewModelScope.launch(Dispatchers.Default) {
        jokeResultDelete.postValue(jokeTasksRepository.deleteJokeResult(joke).toString())
    }*/



}



