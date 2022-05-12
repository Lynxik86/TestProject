package com.example.android_dev.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.android_dev.data.local.ConnectDb
import com.example.android_dev.data.local.dao.ChuckDao
import com.example.android_dev.data.local.dao.JokeDao
import com.example.android_dev.data.local.localdatasource.ChuckLocalDataSource
import com.example.android_dev.data.local.localdatasource.JokeLocalDataSource
import com.example.android_dev.data.remote.ApiManager
import com.example.android_dev.data.remote.remotedatasource.ChuckRemoteDataSource
import com.example.android_dev.data.remote.remotedatasource.JokeRemoteDataSource
import com.example.android_dev.network.interfaces.ChuckApi
import com.example.android_dev.network.interfaces.JokeApi
import com.example.android_dev.repository.ChuckTasksRepository
import com.example.android_dev.repository.JokeTasksRepository
import com.example.android_dev.viewmodel.ChuckViewModel
import com.example.android_dev.viewmodel.JokeViewModel
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * As these types are scoped to the application lifecycle using @Singleton, they're installed
 * in Hilt's ApplicationComponent.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Connect DB
    @Provides
    @Singleton
    internal fun provideConnectDb(@ApplicationContext context: Context): ConnectDb {
        return Room
            .databaseBuilder(
                context.applicationContext,
                ConnectDb::class.java,
                "app_database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    //Joke DB
    @Singleton
    @Provides
    fun provideJokeLocalDataSource(jokeDao: JokeDao): JokeLocalDataSource {
        return JokeLocalDataSource(jokeDao)

    }

    @Provides
    @Singleton
    fun provideJokeDao(connectDb: ConnectDb): JokeDao {
        return connectDb.jokeDao()
    }



    //Chuck DB
    @Singleton
    @Provides
    fun provideChuckLocalDataSource(chuckDao: ChuckDao): ChuckLocalDataSource {
        return ChuckLocalDataSource(chuckDao)

    }

    @Provides
    @Singleton
    fun provideChuckDao(connectDb: ConnectDb): ChuckDao {
        return connectDb.chuckDao()
    }


    //Retrofit
    @Provides
    @Singleton
    internal fun provideOkHttpClientBuilder(): OkHttpClient {
        // val client: OkHttpClient
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder().create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideApiManager(
        gsonConverterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): ApiManager {
        return ApiManager(gsonConverterFactory, client)
    }

    //Joke Network
    @Singleton
    @Provides
    fun provideJokeTasksRepository(/*connectDb: ConnectDb,*/ jokeLocalDataSource: JokeLocalDataSource,  jokeRemoteDataSource: JokeRemoteDataSource): JokeTasksRepository {
        return JokeTasksRepository(/*connectDb, */ jokeLocalDataSource,  jokeRemoteDataSource)

    }

    @Singleton
    @Provides
    fun provideJokeRemoteDataSource(jokeApi: JokeApi): JokeRemoteDataSource {
        return JokeRemoteDataSource(jokeApi)

    }
    @Provides
    @Singleton
    fun provideJokeApi(apiManager: ApiManager): JokeApi {
        return apiManager.getJokeApi()
    }

    //Chuck Network
    @Singleton
    @Provides
    fun provideChuckTasksRepository(/*connectDb: ConnectDb,*/ chuckLocalDataSource: ChuckLocalDataSource, chuckRemoteDataSource: ChuckRemoteDataSource): ChuckTasksRepository {
        return ChuckTasksRepository(/*connectDb,*/ chuckLocalDataSource, chuckRemoteDataSource)

    }

    @Singleton
    @Provides
    fun provideChuckRemoteDataSource(chuckApi: ChuckApi): ChuckRemoteDataSource {
        return ChuckRemoteDataSource(chuckApi)

    }

    @Provides
    @Singleton
    fun provideChuckApi(apiManager: ApiManager): ChuckApi {
        return apiManager.getChuckApi()
    }





}