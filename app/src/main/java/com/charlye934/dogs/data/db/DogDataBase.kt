package com.charlye934.dogs.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.charlye934.dogs.data.model.DogBreed

@Database(entities = arrayOf(DogBreed::class), version = 1)
abstract class DogDataBase: RoomDatabase() {
    companion object{
        @Volatile private var instance: DogDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DogDataBase::class.java,
            "dogdatabase"
        ).build()
    }

}