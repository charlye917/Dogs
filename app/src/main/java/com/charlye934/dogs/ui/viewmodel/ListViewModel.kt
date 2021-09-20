package com.charlye934.dogs.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.domain.DogInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {
    private val interactor = DogInteractor()

    private val _dogsBreed = MutableLiveData<List<DogBreed>>()
    val dogsBreed: LiveData<List<DogBreed>>
        get() = _dogsBreed

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
            get() = _loading

    fun refresh(){
        fetchFromRemote()
    }

    private fun fetchFromRemote(){
        _loading.value = true
        viewModelScope.launch {
            _dogsBreed.postValue(interactor.getDataApi())
            _loading.value = false
        }
    }
}