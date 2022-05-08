package com.example.android_dev.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_dev.data.local.ConnectDb
import com.example.android_dev.data.model.ChuckResult
import com.example.android_dev.repository.ChuckTasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChuckViewModel @Inject constructor (
    application: Application,
    private val chuckTasksRepository: ChuckTasksRepository
    ) : AndroidViewModel(application) {

    /* private val chuckTasksRepository: ChuckTasksRepository =
         ChuckTasksRepository(MyTestDb.getDatabase(application))*/

  /*  private val chuck: ChuckTasksRepository =
        ChuckTasksRepository.getInstance(ConnectDb.getDatabase(application))*/

    internal var _chuckId = MutableLiveData<String>()
    internal val allChucks: LiveData<List<ChuckResult>> = chuckTasksRepository.readAllDataChuck()
    private var allChucksDelete = MutableLiveData<String>()

    fun coroutineGetChuck() = viewModelScope.launch(Dispatchers.Default) {
        _chuckId.postValue(chuckTasksRepository.getChuck().id)
    }

    fun coroutineDeleteChuck() = viewModelScope.launch(Dispatchers.Default) {
        allChucksDelete.postValue(chuckTasksRepository.deleteAllChucks().toString())
    }
}