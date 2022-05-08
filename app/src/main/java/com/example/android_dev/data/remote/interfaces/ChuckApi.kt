package com.example.android_dev.network.interfaces

import com.example.android_dev.data.model.ChuckResult
import retrofit2.http.GET

interface ChuckApi {

    @GET("jokes/random")
    //каждая функция должна быть помечен как отложенная (suspend). асинхронная работа
    //Ретрофит научился работа
    suspend fun randomChuck(): ChuckResult

}

