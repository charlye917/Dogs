package com.charlye934.dogs.data.service

import com.charlye934.dogs.data.model.DogBreed
import retrofit2.Response
import retrofit2.http.GET

interface DogsApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    suspend fun getDogs(): Response<List<DogBreed>>
}