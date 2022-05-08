package com.example.android_dev.data.remote.remotedatasource

import com.example.android_dev.data.model.JokeResult
import com.example.android_dev.network.interfaces.JokeApi
import javax.inject.Inject

class JokeRemoteDataSource
@Inject constructor(
    private val jokeApi: JokeApi
) {

    suspend fun getJokeResult(): JokeResult {
        return jokeApi.randomJoke()
    }
}