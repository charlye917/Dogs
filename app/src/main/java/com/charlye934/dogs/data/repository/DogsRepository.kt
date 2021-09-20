package com.charlye934.dogs.data.repository

import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.data.service.DogApiService
import retrofit2.Response

class DogsRepository {
    private val api = DogApiService()

    suspend fun getDogsApi(): Response<List<DogBreed>>{
        return api.getDogs()
    }
}