package com.example.android_dev.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.db.dao.ChuckDao
import com.example.android_dev.db.dao.JokeDao
import com.example.android_dev.db.dao.RegistryDao
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.model.FormResult
import com.example.android_dev.model.JokeResult
import com.example.android_dev.network.RetrofitClient
import com.example.android_dev.network.interfaces.ChuckApi
import com.example.android_dev.network.interfaces.JokeApi

class TasksRepository(
    private val chuckDao: ChuckDao,
    private val chuckApi: ChuckApi,
    private val jokeDao: JokeDao,
    private val jokeApi: JokeApi,
    private val registryDao: RegistryDao,


) {

    fun readAllDataChuck(): LiveData<List<ChuckResult>> {
        return chuckDao.readAllData()
    }

    fun readAllDataJokes(): LiveData<List<JokeResult>> {
        return jokeDao.readAllData()
    }

    internal suspend fun getChuck(): ChuckResult {
        val chuckResult = chuckApi.randomChuck()

        chuckDao.addRandomChuck(chuckResult)
        //переключает контекст текущей сопрограммы;
        // при выполнении данного блока сопрограмма возвращается в предыдущий контекст
        Log.i("JOKE", chuckResult.id)
        return chuckResult
    }

    internal suspend fun getJokes(): JokeResult {

        val jokeResult = jokeApi.randomJoke()
        jokeDao.addRandomJoke(jokeResult)
        //Send an INFO log message.
        Log.i("JOKE", jokeResult.joke)

        return jokeResult

    }


    fun getFormResultByLogin(firstname: String): FormResult? {

        val formResult=registryDao.getFormByLogin(firstname)
        return formResult

    }


    fun addFormResult (formResult: FormResult){

        registryDao.addRegistryData(formResult)

    }


}