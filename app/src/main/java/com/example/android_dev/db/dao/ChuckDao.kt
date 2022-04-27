package com.example.android_dev.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_dev.model.ChuckResult


@Dao
interface ChuckDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRandomChuck(chuck: ChuckResult)

    @Query("SELECT * FROM chuck_table")
    fun readAllData(): LiveData<List<ChuckResult>>

    @Query("DELETE FROM chuck_table")
    suspend fun deleteAll()


}
