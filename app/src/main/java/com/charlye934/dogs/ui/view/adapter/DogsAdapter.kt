package com.charlye934.dogs.ui.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.charlye934.dogs.R
import com.charlye934.dogs.data.model.DogBreed
import com.charlye934.dogs.databinding.ItemDogBinding
import com.charlye934.dogs.ui.view.ListFragmentDirections
import com.charlye934.dogs.ui.view.listener.DogClickListener

class DogsAdapter(): RecyclerView.Adapter<DogsAdapter.DogsViewHolder>(), DogClickListener {

    private val doglList = arrayListOf<DogBreed>()
    private lateinit var binding: ItemDogBinding

    fun updateDogList(newList: List<DogBreed>){
        doglList.clear()
        doglList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.item_dog, parent, false)
        return DogsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        holder.bindData(doglList[position])
        holder.binding.listener = this
    }

    override fun getItemCount(): Int = doglList.size

    override fun onDogClicked(view: View) {}

    class DogsViewHolder(val binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(dog: DogBreed){
            binding.dogs = dog
            binding.cardDogs.setOnClickListener {
                val uuid = dog.breedId
                val action = ListFragmentDirections.actionListFragmentToDetailFragment()
                action.dogId = uuid!!
                it.findNavController().navigate(action)
            }
        }
    }
}