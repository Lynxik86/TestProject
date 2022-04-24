package com.example.android_dev.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.network.RetrofitClient
import com.example.android_dev.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChuckViewModel(application: Application) : AndroidViewModel(application) {
    private val tasksRepository: TasksRepository =
        TasksRepository(MyTestDb.getDatabase(application).chuckResultDao(),
            RetrofitClient.getChuckApi(), MyTestDb.getDatabase(application).jokeResultDao(),
            RetrofitClient.getJokeApi()
        )

    internal var _chuckId = MutableLiveData<String>()
    internal val allChucks: LiveData<List<ChuckResult>> = tasksRepository.readAllDataChuck()

    fun coroutineGetChuck() = viewModelScope.launch(Dispatchers.Default) {
        _chuckId.postValue(tasksRepository.getChuck().id)
    }
}