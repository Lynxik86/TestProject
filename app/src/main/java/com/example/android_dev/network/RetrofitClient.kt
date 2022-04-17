package com.example.android_dev.network

import com.example.android_dev.network.interfaces.ChuckApi
import com.example.android_dev.network.interfaces.JokeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val JOKE_BASE_URL = "https://icanhazdadjoke.com/"
const val CHUCK_BASE_URL = "https://api.chucknorris.io/"


object RetrofitClient {
    private val client: OkHttpClient

    init {
        //In Retrofit 2, all network operations are performed via OkHttp library.
        //OkHttp provides HttpLoggingInterceptor which logs HTTP request and response data.
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
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