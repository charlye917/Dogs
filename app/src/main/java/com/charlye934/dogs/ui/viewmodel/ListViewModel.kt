package com.charlye934.dogs.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.data.repository.DogsRepository
import com.charlye934.dogs.utils.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: DogsRepository,
    @ApplicationContext val context: Context
    ): ViewModel() {

    private var prefHelper = SharePreferencesHelper(context)
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

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
        checkCacehDuration()
        val updateTime = prefHelper.getUpdateTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime)
            fetchFromDataBase()
        else
            fetchFromRemote()
    }

    fun refreshBypassache(){
        fetchFromRemote()
    }

    private fun checkCacehDuration(){
        val cachePreferences = prefHelper.getCacheDuration()
        try {
            val cachePreferencesInt = cachePreferences?.toInt() ?: 5 * 60
            refreshTime = cachePreferencesInt.times(1000 * 1000 * 1000L)
            Log.d("__tag", refreshTime.toString())
            Log.d("__tag", "entro")
        }catch (e: NumberFormatException){
            Log.d("__tag", "error:  $e")
            e.printStackTrace()
        }
    }

    private fun fetchFromDataBase(){
        _loading.value = true
        viewModelScope.launch {
            val dogs = repository.getAllDogs()
            dogsRetrived(dogs)
            Toast.makeText(context,"room", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote(){
        _loading.value = true
        viewModelScope.launch {
            val data = repository.getDogsApi()
            if(data.isSuccessful){
               storeDogsLocally(data.body()!!)
                Toast.makeText(context,"api", Toast.LENGTH_SHORT).show()
            }else{
                _loading.value = false
                _dogsLoadError.value = true
            }
        }
    }

    private fun storeDogsLocally(list: List<DogBreed>){
        viewModelScope.launch(Dispatchers.IO){
            repository.clearAllDogs()
            val result = repository.saveAllDogs(list)
            var i = 0
            while (i < list.size){
                list[i].uuid = result[i].toInt()
                i++
            }
            dogsRetrived(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    private fun dogsRetrived(list: List<DogBreed>) {
        _dogsBreed.postValue(list)
        _dogsLoadError.postValue(false)
        _loading.postValue(false)
    }
}