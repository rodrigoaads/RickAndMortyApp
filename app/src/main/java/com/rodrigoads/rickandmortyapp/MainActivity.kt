package com.rodrigoads.rickandmortyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rodrigoads.rickandmortyapp.databinding.ActivityMainBinding
import com.rodrigoads.rickandmortyapp.fragments.CharacterFragment
import com.rodrigoads.rickandmortyapp.fragments.EpisodeFragment
import com.rodrigoads.rickandmortyapp.fragments.LocationFragment
import com.rodrigoads.rickandmortyapp.fragments.SupportFragment
import com.rodrigoads.rickandmortyapp.viewmodel.CharacterViewModel
import com.rodrigoads.rickandmortyapp.viewmodel.EpisodeViewModel
import com.rodrigoads.rickandmortyapp.viewmodel.LocationViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val supportFragment = SupportFragment()

    private val characterViewModel: CharacterViewModel by viewModels()
    private val episodeViewModel: EpisodeViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()

    private var initLoadings = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        getClickBottomNavigation()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = null
    }

    private fun getClickBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.character_page -> {
                    supportFragment.startFragment(CharacterFragment(), this)
                }
                R.id.location_page -> {
                    supportFragment.startFragment(LocationFragment(), this)
                }
                R.id.episode_page -> {
                    supportFragment.startFragment(EpisodeFragment(), this)
                }
                else -> false
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        if (!initLoadings) {
            lifecycleScope.launch(Dispatchers.IO) {
                delay(1500)
                characterViewModel.getInfos()
                locationViewModel.getInfos()
                episodeViewModel.getInfos()
                initLoadings = true
                withContext(Main) {
                    binding.progressBarInit.visibility = View.GONE
                }
            }
        }
    }
}