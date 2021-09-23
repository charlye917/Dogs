package com.charlye934.dogs.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlye934.dogs.databinding.FragmentListBinding
import com.charlye934.dogs.ui.view.adapter.DogsAdapter
import com.charlye934.dogs.ui.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    private lateinit var dogAdapter: DogsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterConfiguration()
        observerViewmodel()
    }

    private fun adapterConfiguration(){
        dogAdapter = DogsAdapter()
        binding.rvDogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogAdapter
        }
    }

    private fun observerViewmodel(){
        viewModel.refresh()

        viewModel.dogsBreed.observe(viewLifecycleOwner, Observer{ dogs ->
            dogs?.let {
                binding.rvDogsList.visibility = View.VISIBLE
                dogAdapter.updateDogList(it)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loader ->
            loader?.let {
                binding.loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    binding.listError.visibility = View.GONE
                    binding.rvDogsList.visibility = View.GONE
                }
            }
        })

        viewModel.dogsLoadError.observe(viewLifecycleOwner, Observer { error ->
            binding.listError.visibility = if(error) View.VISIBLE else  View.GONE
        })
    }
}