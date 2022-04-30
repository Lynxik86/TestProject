package com.example.android_dev.repository

import androidx.lifecycle.LiveData
import com.example.android_dev.db.LocalDataSourceChuck
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.db.RemoteDataSourceChuck
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.network.RetrofitClient

class TasksRepositoryChuck private constructor(private val connectDb: MyTestDb) : DataSourceChuck {

    companion object {
        @Volatile
        private var INSTANCE: TasksRepositoryChuck? = null

        fun getInstance(connectDb: MyTestDb): TasksRepositoryChuck =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TasksRepositoryChuck(connectDb).also { INSTANCE = it }
            }
    }

    val localDataSourceChuck: LocalDataSourceChuck =
        LocalDataSourceChuck(
            connectDb.chuckResultDao(),
        )

    val remoteDataSourceChuck: RemoteDataSourceChuck =
        RemoteDataSourceChuck(
            RetrofitClient.getChuckApi()
        )

    override fun readAllDataChuck(): LiveData<List<ChuckResult>> {
        return localDataSourceChuck.readChuckResult()
    }


    override suspend fun getChuck(): ChuckResult {
        val chuckResult = remoteDataSourceChuck.getChuckResult()
        return localDataSourceChuck.postChuck(chuckResult)
    }
}

/* companion object {
      fun getInstance(): TasksRepositoryChuck? {
          var instance: TasksRepositoryChuck? = null
          val initialized = AtomicBoolean()
          if (initialized.getAndSet(true)) {
              instance = TasksRepositoryChuck(connectDb)
          }
          return instance
      }
  }*/

/*companion object {
    @Volatile
    private var INSTANCE: Singleton? = null

    fun getInstance(application: Application): Singleton =
        INSTANCE ?: synchronized(this) {
            INSTANCE
                ?: Singleton(application).also { INSTANCE = it }
        }
}*/

/*fun getInstance(): TasksRepositoryChuck {
    val instance: TasksRepositoryChuck
    val initialized = AtomicBoolean()
    if (initialized.getAndSet(true)) {
        instance = TasksRepositoryChuck( connectDb )
    }
    return instance
}*/


/*{
        if (instance == null) {
            synchronized(this) {
                if (instance == null) {
                    val slothchuck = TasksRepositoryChuck()
                    instance = slothchuck
                    return slothchuck
                }
            }
        }
        return instance!!
    }*/


/*
class TasksRepositoryChuck(
    private val connectDb: MyTestDb
) : DataSourceChuck {

        val localDataSourceChuck: LocalDataSourceChuck =
            LocalDataSourceChuck(
                connectDb.chuckResultDao(),
            )

        val remoteDataSourceChuck: RemoteDataSourceChuck =
            RemoteDataSourceChuck(
                RetrofitClient.getChuckApi()
            )

    override fun readAllDataChuck(): LiveData<List<ChuckResult>> {
        return localDataSourceChuck.readChuckResult()
    }


    override suspend fun getChuck(): ChuckResult {
        val chuckResult = remoteDataSourceChuck.getChuckResult()
        return localDataSourceChuck.postChuck(chuckResult)
    }
}
*/


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