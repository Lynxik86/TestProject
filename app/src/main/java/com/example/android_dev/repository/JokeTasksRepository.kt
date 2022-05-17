package com.example.android_dev.repository

import androidx.lifecycle.LiveData
import com.example.android_dev.data.local.localdatasource.JokeLocalDataSource
import com.example.android_dev.data.model.JokeResult
import com.example.android_dev.data.remote.remotedatasource.JokeRemoteDataSource
import com.example.android_dev.repository.source.JokeDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokeTasksRepository
@Inject constructor(
   // private val connectDb: ConnectDb,
    private val jokeLocalDataSource: JokeLocalDataSource,
    private val jokeRemoteDataSource: JokeRemoteDataSource
    ) : JokeDataSource {

   /* companion object {
        @Volatile
        private var INSTANCE: JokeTasksRepository? = null

        fun getInstance(connectDb: ConnectDb): JokeTasksRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: JokeTasksRepository(connectDb).also { INSTANCE = it }
            }
    }*/

  /*  private val jokeLocalDataSource: JokeLocalDataSource =
        JokeLocalDataSource(
            connectDb.jokeResultDao(),
        )

    private val jokeRemoteDataSource: JokeRemoteDataSource =
        JokeRemoteDataSource(
            RetrofitClient.getJokeApi()
        )*/

    override fun readAllDataJokes(): LiveData<List<JokeResult>> {
        return jokeLocalDataSource.readJokeResult()

    }
   // override  fun getJokes(): JokeResult {
    override suspend fun getJokes(): JokeResult {

        val jokeResult = jokeRemoteDataSource.getJokeResult()
        return jokeLocalDataSource.postJoke(jokeResult)
    }

    override suspend fun deleteAllJokes(){
        jokeLocalDataSource.deleteJokes()
    }

    override suspend fun deleteJokeResult(joke:String){
        jokeLocalDataSource.deleteJoke(joke)
    }

}