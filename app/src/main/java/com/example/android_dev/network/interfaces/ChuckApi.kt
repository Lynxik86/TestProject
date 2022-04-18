package com.example.android_dev.network.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_dev.model.ChuckResult
import retrofit2.http.GET

interface ChuckApi {

    @GET("jokes/random")
    //каждая функция должна быть помечен как отложенная (suspend). асинхронная работа
    //Ретрофит научился работать с котлиновскими suspend функциями с версии 2.6.0,
    // теперь он напрямую выполняет сетевой запрос и возвращает объект с данными:
    suspend fun randomChuck(): ChuckResult

}

@Dao
interface ChuckApiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRandomChuck(chuck: ChuckResult)

    @Query("SELECT * FROM chuck_table")
    fun readAllData(): LiveData<List<ChuckResult>>

}