package com.charlye934.dogs.ui.view.listener

import android.view.View
import com.charlye934.dogs.databinding.ItemDogBinding

interface DogClickListener {
    fun onDogClicked(binding: ItemDogBinding)
}