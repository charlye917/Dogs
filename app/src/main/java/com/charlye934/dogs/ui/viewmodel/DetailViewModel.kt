package com.charlye934.dogs.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlye934.dogs.data.db.DogDatabase
import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.data.repository.DogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DogsRepository,
    @ApplicationContext val context: Context
): ViewModel() {

    private val _dogsBreed = MutableLiveData<DogBreed>()
    val dogsBreed: LiveData<DogBreed>
        get() = _dogsBreed

    fun fetch(uuid: String){
        viewModelScope.launch{
            val dog = repository.getDog(uuid)
            _dogsBreed.postValue(dog)
        }
    }
}