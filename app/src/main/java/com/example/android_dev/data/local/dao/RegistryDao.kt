package com.example.android_dev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_dev.data.model.FormResult

@Dao
interface RegistryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRegistryData(form: FormResult)

    @Query("SELECT * FROM form_table where first_name=:login")
    fun getFormByLogin(login: String): FormResult?

    @Query("DELETE FROM form_table where not first_name='admin'")
    fun deleteAllNotAdmin()
}