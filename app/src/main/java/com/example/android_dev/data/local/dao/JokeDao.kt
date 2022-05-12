package com.example.android_dev.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_dev.data.model.JokeResult

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRandomJoke(joke: JokeResult)

    @Query("SELECT * FROM joke_table")
    fun readAllData(): LiveData<List<JokeResult>>

    @Query("DELETE FROM joke_table")
    suspend fun deleteAll()

    @Query("DELETE FROM joke_table where joke=:joke")
    suspend fun deleteJokeResult(joke: String)
}