package com.charlye934.dogs.data.service

import com.charlye934.dogs.BuildConfig
import com.charlye934.dogs.data.model.DogBreed
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogApiService {
    private val api = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DogsApi::class.java)

    suspend fun getDogs(): Response<List<DogBreed>>{
        return api.getDogs()
    }

}