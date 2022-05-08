package com.example.android_dev.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName= "joke_table")
data class JokeResult(
    @PrimaryKey
    val id: String,
    val joke: String,
    val status: Int,

)
