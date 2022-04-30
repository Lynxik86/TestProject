package com.example.android_dev.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.repository.TasksRepositoryChuck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChuckViewModel(application: Application) : AndroidViewModel(application) {
   /* private val tasksRepositoryChuck: TasksRepositoryChuck =
        TasksRepositoryChuck(MyTestDb.getDatabase(application))*/
   private val tasksRepositoryChuck: TasksRepositoryChuck=
       TasksRepositoryChuck.getInstance(MyTestDb.getDatabase(application))

    internal var _chuckId = MutableLiveData<String>()
    internal val allChucks: LiveData<List<ChuckResult>> = tasksRepositoryChuck.readAllDataChuck()

    fun coroutineGetChuck() = viewModelScope.launch(Dispatchers.Default) {
        _chuckId.postValue(tasksRepositoryChuck.getChuck().id)
    }
}