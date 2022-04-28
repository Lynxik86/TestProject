package com.example.android_dev.db

import com.example.android_dev.model.JokeResult
import com.example.android_dev.network.interfaces.JokeApi

class RemoteDataSourceJoke(
    private val jokeApi: JokeApi
) {

    suspend fun getJokeResult(): JokeResult {

        return jokeApi.randomJoke()

    }
}