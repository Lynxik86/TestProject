package com.example.android_dev.data.remote.remotedatasource

import com.example.android_dev.data.model.ChuckResult
import com.example.android_dev.network.interfaces.ChuckApi
import javax.inject.Inject

class ChuckRemoteDataSource @Inject constructor (
    private val chuckApi: ChuckApi,
) {

    suspend fun getChuckResult(): ChuckResult {

        return chuckApi.randomChuck()

    }

}