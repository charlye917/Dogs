package com.charlye934.dogs.domain

import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.data.repository.DogsRepository

class DogInteractor {

    private val repository = DogsRepository()

    suspend fun getDataApi(): List<DogBreed>{
        val data = repository.getDogsApi()
        return if(data.isSuccessful) data.body()!! else arrayListOf<DogBreed>()
    }
}