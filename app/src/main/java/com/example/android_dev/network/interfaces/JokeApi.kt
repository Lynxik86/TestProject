package com.example.android_dev.network.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_dev.model.JokeResult
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeApi {
    @Headers("Accept: application/json")
    @GET(".")
    //каждая функция должна быть помечен как отложенная (suspend). асинхронная работа
    suspend fun randomJoke(): JokeResult
}

@Dao
interface JokeApiDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRandomJoke(joke: JokeResult)

    @Query("SELECT * FROM chuck_table")
    fun readAllData(): LiveData<List<JokeResult>>
}
