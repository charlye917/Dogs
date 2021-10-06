package com.charlye934.dogs.data.repository

import android.content.Context
import android.util.Log
import com.charlye934.dogs.data.db.DogDao
import com.charlye934.dogs.data.db.DogDatabase
import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.data.service.DogApiService
import com.charlye934.dogs.data.service.DogsApi
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class DogsRepository @Inject constructor(
    private val dogApi: DogApiService,
    @ApplicationContext context: Context
) {
    private var dogDao = DogDatabase(context).dogDao()

    suspend fun getDogsApi(): Response<List<DogBreed>>{
        return dogApi.getDogs()
    }

    suspend fun saveAllDogs(dogs: List<DogBreed>): List<Long>{
        return dogDao.insertAll(*dogs.toTypedArray())
    }

    suspend fun clearAllDogs(){
        dogDao.deleteAllDogs()
    }

    suspend fun getAllDogs(): List<DogBreed>{
        return dogDao.getAllDogs()
    }

    suspend fun getDog(dogId: String): DogBreed{
        return dogDao.getDog(dogId)
    }
}