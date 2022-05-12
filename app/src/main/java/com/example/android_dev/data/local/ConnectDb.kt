package com.example.android_dev.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_dev.data.local.dao.ChuckDao
import com.example.android_dev.data.local.dao.JokeDao
import com.example.android_dev.data.local.dao.RegistryDao
import com.example.android_dev.data.model.ChuckResult
import com.example.android_dev.data.model.FormResult
import com.example.android_dev.data.model.JokeResult

@Database(entities = [(ChuckResult::class), (JokeResult::class), (FormResult::class)], version = 5) /*,  exportSchema = false)*/
abstract class ConnectDb : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
    abstract fun chuckDao(): ChuckDao
    abstract fun registryDao(): RegistryDao

  /*  companion object {
        @Volatile
        private var dbInstance: ConnectDb? = null

        fun getDatabase(context: Context): ConnectDb {
            val tempInstance = dbInstance

            if (tempInstance != null) {
                return tempInstance
            }

            return dbInstance ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        ConnectDb::class.java,
                        "app_database"
                    )
                    //.createFromAsset("database/user.db")
                    .fallbackToDestructiveMigration()
                    //.allowMainThreadQueries()
                    .build()
                dbInstance = instance
                return instance

            }
        }
    }*/
}