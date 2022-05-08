package com.example.android_dev.network.interfaces

import com.example.android_dev.data.model.JokeResult
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeApi {
    @Headers("Accept: application/json")
    @GET(".")
    //каждая функция должна быть помечен как отложенная (suspend). асинхронная работа
    suspend fun randomJoke(): JokeResult
}


