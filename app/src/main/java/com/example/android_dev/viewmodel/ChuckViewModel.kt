package com.example.android_dev.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android_dev.data.model.ChuckResult
import com.example.android_dev.data.model.JokeResult
import com.example.android_dev.repository.ChuckTasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChuckViewModel @Inject constructor (
    application: Application,
    private val chuckTasksRepository: ChuckTasksRepository
    ) : AndroidViewModel(application) {

    /*private val chuckTasksRepository: ChuckTasksRepository =
         ChuckTasksRepository(MyTestDb.getDatabase(application))*/

    /*private val chuck: ChuckTasksRepository =
        ChuckTasksRepository.getInstance(ConnectDb.getDatabase(application))*/

    internal val _chuckId = MutableStateFlow("")

    fun coroutineGetChuck() = viewModelScope.launch(Dispatchers.Default) {
        _chuckId.emit(chuckTasksRepository.getChuck().id)
    }

    val allChucks: Flow<List<ChuckResult>> = chuckTasksRepository.readAllDataChuck()

    private var chuckResultDelete = MutableStateFlow("")

    fun coroutineDeleteChuckResult(chuck: String) = viewModelScope.launch(Dispatchers.Default) {
        chuckResultDelete.emit(chuckTasksRepository.deleteChuckResult(chuck).toString())
    }

    private var allChucksDelete = MutableStateFlow("")
    fun coroutineDeleteChuck() = viewModelScope.launch(Dispatchers.Default) {
        allChucksDelete.emit(chuckTasksRepository.deleteAllChucks().toString())
    }

    /*internal var _chuckId = MutableLiveData<String>()
    internal val allChucks: LiveData<List<ChuckResult>> = chuckTasksRepository.readAllDataChuck()
    private var allChucksDelete = MutableLiveData<String>()
    private var chuckResultDelete = MutableLiveData<String>()

    fun coroutineGetChuck() = viewModelScope.launch(Dispatchers.Default) {
        _chuckId.postValue(chuckTasksRepository.getChuck().id)
    }

    fun coroutineDeleteChuck() = viewModelScope.launch(Dispatchers.Default) {
        allChucksDelete.postValue(chuckTasksRepository.deleteAllChucks().toString())
    }

    fun coroutineDeleteChuckResult(chuck:String) = viewModelScope.launch(Dispatchers.Default) {
        chuckResultDelete.postValue(chuckTasksRepository.deleteChuckResult(chuck).toString())
    }*/
}