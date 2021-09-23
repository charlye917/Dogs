package com.charlye934.dogs.domain

import android.util.Log
import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.data.repository.DogsRepository

class DogInteractor {

    private val repository = DogsRepository()

    suspend fun getDataApi(): List<DogBreed>{
        val data = repository.getDogsApi()
        Log.d("__tag", data.toString())
        return if(data.isSuccessful) data.body()!! else arrayListOf<DogBreed>()
    }
}