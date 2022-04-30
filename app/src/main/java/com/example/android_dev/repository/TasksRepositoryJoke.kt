package com.example.android_dev.repository

import androidx.lifecycle.LiveData
import com.example.android_dev.db.LocalDataSourceJoke
import com.example.android_dev.db.MyTestDb
import com.example.android_dev.db.RemoteDataSourceJoke
import com.example.android_dev.model.JokeResult
import com.example.android_dev.network.RetrofitClient

class TasksRepositoryJoke private constructor(private val connectDb: MyTestDb) : DataSourceJoke {

    companion object {
        @Volatile
        private var INSTANCE: TasksRepositoryJoke? = null

        fun getInstance(connectDb: MyTestDb): TasksRepositoryJoke =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TasksRepositoryJoke(connectDb).also { INSTANCE = it }
            }
    }

    private val localDataSourceJoke: LocalDataSourceJoke =
        LocalDataSourceJoke(
            connectDb.jokeResultDao(),
        )

    private val remoteDataSourceJoke: RemoteDataSourceJoke =
        RemoteDataSourceJoke(
            RetrofitClient.getJokeApi()
        )

    override fun readAllDataJokes(): LiveData<List<JokeResult>> {
        return localDataSourceJoke.readJokeResult()

    }

    override suspend fun getJokes(): JokeResult {

        val jokeResult = remoteDataSourceJoke.getJokeResult()
        return localDataSourceJoke.postJoke(jokeResult)


    }
}