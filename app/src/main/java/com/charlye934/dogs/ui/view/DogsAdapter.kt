package com.charlye934.dogs.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.databinding.ItemDogBinding

class DogsAdapter(): RecyclerView.Adapter<DogsAdapter.DogsViewHolder>() {

    private val doglList = arrayListOf<DogBreed>()

    fun updateDogList(newList: List<DogBreed>){
        doglList.clear()
        doglList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        val binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = doglList.size

    class DogsViewHolder(binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dog: DogBreed){

        }
    }
}