package com.example.android_dev.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.android_dev.model.FormResult

@Dao
interface RegistryApiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRegistryData(form: FormResult)

}