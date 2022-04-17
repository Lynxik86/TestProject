package com.example.android_dev.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_dev.db.dao.ChuckApiDao
import com.example.android_dev.db.dao.JokeApiDao
import com.example.android_dev.db.dao.RegistryApiDao
import com.example.android_dev.model.ChuckResult
import com.example.android_dev.model.FormResult
import com.example.android_dev.model.JokeResult

@Database(entities = [(ChuckResult::class), (JokeResult::class), (FormResult::class)],  version = 3)
abstract class MyTestDb : RoomDatabase() {
    abstract fun jokeResultDao(): JokeApiDao
    abstract fun chuckResultDao(): ChuckApiDao
    abstract fun formResultDao(): RegistryApiDao

    companion object {
        @Volatile
        private var dbInstance: MyTestDb? = null

        fun getDatabase(context: Context): MyTestDb {
            val tempInstance = dbInstance

            if (tempInstance != null) {
                return tempInstance
            }

            return dbInstance ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        MyTestDb::class.java,
                        "app_database"
                    )
//                    .createFromAsset("database/user.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                dbInstance = instance
                return instance
            }
        }
    }
}