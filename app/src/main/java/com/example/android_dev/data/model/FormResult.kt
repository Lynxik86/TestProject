package com.example.android_dev.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="form_table")
data class FormResult(
    @PrimaryKey
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,

)