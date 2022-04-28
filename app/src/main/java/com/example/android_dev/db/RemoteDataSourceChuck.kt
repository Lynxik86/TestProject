package com.example.android_dev.db

import com.example.android_dev.model.ChuckResult
import com.example.android_dev.network.interfaces.ChuckApi

class RemoteDataSourceChuck(
    private val chuckApi: ChuckApi,
) {

    suspend fun getChuckResult(): ChuckResult {

        return chuckApi.randomChuck()

    }

}