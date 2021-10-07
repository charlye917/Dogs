package com.charlye934.dogs.ui.view.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.charlye934.dogs.R
import com.charlye934.dogs.databinding.FragmentListBinding
import com.charlye934.dogs.ui.view.adapter.DogsAdapter
import com.charlye934.dogs.ui.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    private lateinit var dogAdapter: DogsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnRefreshListener()
        adapterConfiguration()
        observerViewmodel()
    }

    private fun setOnRefreshListener(){
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refreshBypassache()
            binding.apply{
                rvDogsList.visibility = View.GONE
                listError.visibility = View.GONE
                loadingView.visibility = View.VISIBLE
                refreshLayout.isRefreshing = false
            }
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionSettings -> {
                val action = ListFragmentDirections.actionListFragmentToSettingsFragment()
                requireView().findNavController().navigate(action)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}