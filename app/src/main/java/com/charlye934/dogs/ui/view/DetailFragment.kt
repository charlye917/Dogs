package com.charlye934.dogs.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.charlye934.dogs.R
import com.charlye934.dogs.data.model.DogPalette
import com.charlye934.dogs.databinding.FragmentDetailBinding
import com.charlye934.dogs.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private var dogUuid = "0"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { dogUuid = DetailFragmentArgs.fromBundle(it).dogId }
        observerViewModel()
    }

    private fun observerViewModel(){
        viewModel.fetch(dogUuid)

        viewModel.dogsBreed.observe(viewLifecycleOwner, Observer {
            binding.dog = it

            it.imageUrl?.let {
                setUpBackgroundColor(it)
            }
        })
    }

    private fun setUpBackgroundColor(url: String){
        Glide.with(requireContext())
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate{ palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            val myPalette = DogPalette(intColor)
                            binding.palette = myPalette
                        }
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}