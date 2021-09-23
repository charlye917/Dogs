package com.charlye934.dogs.ui.viewmodel

import android.util.Log
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

    private val _dogsLoadError = MutableLiveData<Boolean>()
    val dogsLoadError: LiveData<Boolean>
        get() = _dogsLoadError

    fun refresh(){
        fetchFromRemote()
    }

    private fun fetchFromRemote(){
        _loading.value = true
        viewModelScope.launch {
            _loading.value = false
            val data = interactor.getDataApi()
            Log.d("__tag",data.toString())
            if(!data.isNullOrEmpty()){
                _dogsBreed.postValue(data)
                _dogsLoadError.postValue(false)
            }else{
                _dogsLoadError.postValue(true)
            }
        }
    }
}