package com.example.android_dev.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="chuck_table")
data class ChuckResult(
    @PrimaryKey
    val id: String,
    val created_at: String,
    val icon_url: String,
    val updated_at: String,
    val url: String,
    val value: String,

)