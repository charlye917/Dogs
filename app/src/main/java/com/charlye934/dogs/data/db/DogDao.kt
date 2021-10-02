package com.charlye934.dogs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.charlye934.dogs.data.model.DogBreed

@Dao
interface DogDao {
    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FROM dogbreed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dogbreed WHERE breed_id = :dogId")
    suspend fun getDog(dogId: String): DogBreed

    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs()
}