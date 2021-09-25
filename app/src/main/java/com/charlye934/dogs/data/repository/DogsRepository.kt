package com.charlye934.dogs.data.repository

import com.charlye934.dogs.data.db.DogDao
import com.charlye934.dogs.data.db.DogDataBase
import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.data.service.DogApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DogsRepository {
    private val dogApi = DogApiService()
    private lateinit var dogDao: DogDao

    suspend fun getDogsApi(): Response<List<DogBreed>>{
        return dogApi.getDogs()
    }

    suspend fun saveAllDogs(listDogs: List<DogBreed>){
        CoroutineScope(Dispatchers.IO).launch {
            dogDao.insertAll(listDogs)
        }
    }

    suspend fun clearAllDogs(){
        CoroutineScope(Dispatchers.IO).launch {
            dogDao.deleteAllDogs()
        }
    }

    suspend fun getAllDogs(): List<DogBreed>{
        return dogDao.getAllDogs()
    }


}