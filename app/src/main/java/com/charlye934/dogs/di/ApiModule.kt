package com.charlye934.dogs.di

import android.content.Context
import androidx.room.Room
import com.charlye934.dogs.BuildConfig
import com.charlye934.dogs.data.db.DogDatabase
import com.charlye934.dogs.data.service.DogsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDogsApi(retrofit: Retrofit): DogsApi{
        return retrofit.create(DogsApi::class.java)
    }
}