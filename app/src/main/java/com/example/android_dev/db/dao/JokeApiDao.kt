package com.example.android_dev.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_dev.model.JokeResult

@Dao
interface JokeApiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRandomJoke(joke: JokeResult)

    @Query("SELECT * FROM joke_table")
    fun readAllData(): LiveData<List<JokeResult>>

    @Query("DELETE FROM joke_table")
    suspend fun deleteAll()


}