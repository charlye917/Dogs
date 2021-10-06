package com.charlye934.dogs.data.service

import com.charlye934.dogs.BuildConfig
import com.charlye934.dogs.data.model.DogBreed
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class DogApiService @Inject constructor(
    private val api: DogsApi
) {
    suspend fun getDogs(): Response<List<DogBreed>>{
        return api.getDogs()
    }

}