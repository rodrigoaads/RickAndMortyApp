package com.rodrigoads.rickandmortyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.rodrigoads.rickandmortyapp.adapter.LocationAdapter
import com.rodrigoads.rickandmortyapp.databinding.LocationFragmentBinding
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.location.Location
import com.rodrigoads.rickandmortyapp.viewmodel.LocationViewModel
import com.rodrigoads.rickandmortyapp.viewmodel.GetLocationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LocationFragment : Fragment(), getObjectInfos {
    lateinit var binding: LocationFragmentBinding
    private val locationViewModel: LocationViewModel by activityViewModels()
    private val getLocationViewModel: GetLocationViewModel by activityViewModels()
    private val supportFragment = SupportFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LocationFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationViewModel.resultLocationApi.observe(this, Observer {
            keepRecyclerViewLocation(it.locationList)
        })

        binding.progressBarLocation.visibility = View.GONE
        getClicks()
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun keepRecyclerViewLocation(locationList: List<Location>) {
        binding.recyclerViewLocation.apply {
            adapter = LocationAdapter(locationList, this@LocationFragment)
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun getLocation(location: Location) {
        getLocationViewModel.setLocation(location)
        val locationItemFragment = LocationItemFragment()
        supportFragment.startFragment(locationItemFragment, activity, true)
    }

    private fun getClicks() {
        binding.imageViewRefreshLocation.setOnClickListener {
            binding.progressBarLocation.visibility = View.VISIBLE
            refreshList()
        }
    }

    private fun refreshList() {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1500)
            locationViewModel.getInfos()
            withContext(Dispatchers.Main) {
                binding.progressBarLocation.visibility = View.GONE
            }
        }
    }
}