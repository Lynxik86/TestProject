package com.example.android_dev.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_dev.data.model.ChuckResult
import kotlinx.coroutines.flow.Flow


@Dao
interface ChuckDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRandomChuck(chuck: ChuckResult)

    @Query("SELECT * FROM chuck_table")
    fun readAllData(): Flow<List<ChuckResult>>

    @Query("DELETE FROM chuck_table")
    suspend fun deleteAll()

    @Query("DELETE FROM chuck_table where id=:id")
    suspend fun deleteChuckResult(id: String)
}
