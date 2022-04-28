package com.example.android_dev.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android_dev.db.dao.ChuckDao
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.network.interfaces.ChuckApi


class TasksRepositoryChuck(
    private val chuckDao: ChuckDao,
    private val chuckApi: ChuckApi,
) : DataSourceChuck {

    override fun readAllDataChuck(): LiveData<List<ChuckResult>> {
        return chuckDao.readAllData()
    }

    override suspend fun getChuck(): ChuckResult {
        val chuckResult = chuckApi.randomChuck()

        chuckDao.addRandomChuck(chuckResult)
        //переключает контекст текущей сопрограммы;
        // при выполнении данного блока сопрограмма возвращается в предыдущий контекст
        Log.i("JOKE", chuckResult.id)
        return chuckResult
    }
}
/*class TasksRepositoryChuck(
    private val chuckDao: ChuckDao,
    private val chuckApi: ChuckApi,
    private val jokeDao: JokeDao,
    private val jokeApi: JokeApi,
    private val registryDao: RegistryDao,
): DataSource{



    override fun readAllDataChuck(): LiveData<List<ChuckResult>> {
        return chuckDao.readAllData()
    }

    override fun readAllDataJokes(): LiveData<List<JokeResult>> {
        return jokeDao.readAllData()
    }

     override suspend fun getChuck(): ChuckResult {
        val chuckResult = chuckApi.randomChuck()

        chuckDao.addRandomChuck(chuckResult)
        //переключает контекст текущей сопрограммы;
        // при выполнении данного блока сопрограмма возвращается в предыдущий контекст
        Log.i("JOKE", chuckResult.id)
        return chuckResult
    }

     override suspend fun getJokes(): JokeResult {

        val jokeResult = jokeApi.randomJoke()
        jokeDao.addRandomJoke(jokeResult)
        //Send an INFO log message.
        Log.i("JOKE", jokeResult.joke)

        return jokeResult

    }


    override fun getFormResultByLogin(firstname: String): FormResult? {

        val formResult=registryDao.getFormByLogin(firstname)
        return formResult

    }


    override fun addFormResult (formResult: FormResult){

        registryDao.addRegistryData(formResult)

    }


}*/