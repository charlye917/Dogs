package com.charlye934.dogs.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.databinding.ItemDogBinding
import com.charlye934.dogs.ui.view.ListFragmentDirections
import com.charlye934.dogs.ui.view.listener.DogClickListener

class DogsAdapter(): RecyclerView.Adapter<DogsAdapter.DogsViewHolder>(), DogClickListener {

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
        holder.bind(doglList[position])
    }

    override fun getItemCount(): Int = doglList.size

    override fun onDogClicked(binding: ItemDogBinding) {
        val uuid = binding.dogId.text.toString().toInt()
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        action.dogId = uuid
        Navigation.findNavController(binding.root).navigate(action)

    }

    class DogsViewHolder(val binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dog: DogBreed){
            binding.dogs = dog
        }
    }
}