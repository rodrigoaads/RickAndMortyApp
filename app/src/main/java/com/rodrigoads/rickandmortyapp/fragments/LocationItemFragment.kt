package com.rodrigoads.rickandmortyapp.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.rodrigoads.rickandmortyapp.R
import com.rodrigoads.rickandmortyapp.adapter.CharacterAdapter
import com.rodrigoads.rickandmortyapp.databinding.LocationItemFragmentBinding
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.character.Character
import com.rodrigoads.rickandmortyapp.model.api.location.Location
import com.rodrigoads.rickandmortyapp.viewmodel.GetCharacterViewModel
import com.rodrigoads.rickandmortyapp.viewmodel.GetLocationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationItemFragment : Fragment(), getObjectInfos {
    lateinit var binding: LocationItemFragmentBinding
    private val getLocationViewModel: GetLocationViewModel by activityViewModels()
    private val getCharacterViewModel: GetCharacterViewModel by activityViewModels()
    private val supportFragment = SupportFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LocationItemFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getLocationViewModel.locationInfos.observe(this, Observer {
            setLocationInfos(it)
        })
        getLocationViewModel.residentsList.observe(this, Observer {
            keepRecyclerViewCharacter(it.sortedBy { it.characterId })
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun keepRecyclerViewCharacter(characterList: List<Character>) {
        binding.recyclerViewResidentItem.apply {
            adapter = CharacterAdapter(characterList, this@LocationItemFragment, context)
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun getCharacter(character: Character) {
        getCharacterViewModel.setCharacter(character)
        val characterItemFragment = CharacterItemFragment()
        supportFragment.startFragment(characterItemFragment, activity)
    }

    private fun setLocationInfos(location: Location) {
        binding.textViewLocationItemName.text = location.locationName
        (getString(R.string.location_dimensions_item) + location.locationDimension).also {
            binding.textViewLocationItemDimension.text = it
        }
        (getString(R.string.location_type_item) + location.locationType).also {
            binding.textViewLocationItemType.text = it
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1500)
            getLocationViewModel.getResidents()
            withContext(Dispatchers.Main) {
                binding.progressBarResidentItem.visibility = View.GONE
            }
        }
    }
}