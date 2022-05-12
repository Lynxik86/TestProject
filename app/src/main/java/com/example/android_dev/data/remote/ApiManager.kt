package com.example.android_dev.data.remote

import com.example.android_dev.network.interfaces.ChuckApi
import com.example.android_dev.network.interfaces.JokeApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

const val JOKE_BASE_URL = "https://icanhazdadjoke.com/"
const val CHUCK_BASE_URL = "https://api.chucknorris.io/"


class ApiManager  constructor(
    private val gsonConverterFactory: GsonConverterFactory,
    private val client: OkHttpClient
) {

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    fun getJokeApi(): JokeApi {
        return getRetrofit(JOKE_BASE_URL).create(JokeApi::class.java)
    }

    fun getChuckApi(): ChuckApi {
        return getRetrofit(CHUCK_BASE_URL).create(ChuckApi::class.java)
    }
}