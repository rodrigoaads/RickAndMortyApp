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
import com.rodrigoads.rickandmortyapp.adapter.CharacterAdapter
import com.rodrigoads.rickandmortyapp.databinding.CharacterFragmentBinding
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.character.Character
import com.rodrigoads.rickandmortyapp.viewmodel.CharacterViewModel
import com.rodrigoads.rickandmortyapp.viewmodel.GetCharacterViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterFragment : Fragment(), getObjectInfos {
    lateinit var binding: CharacterFragmentBinding
    private val characterViewModel: CharacterViewModel by activityViewModels()
    private val getCharacterViewModel: GetCharacterViewModel by activityViewModels()
    private val supportFragment = SupportFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterViewModel.resultCharacterApi.observe(this, Observer {
            keepRecyclerViewCharacter(it.characterList)
        })

        binding.progressBarCharacter.visibility = View.GONE
        getClicks()
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun keepRecyclerViewCharacter(characterList: List<Character>) {
        binding.recyclerViewCharacter.apply {
            adapter = CharacterAdapter(characterList, this@CharacterFragment, context)
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun getCharacter(character: Character) {
        getCharacterViewModel.setCharacter(character)
        val characterItemFragment = CharacterItemFragment()
        supportFragment.startFragment(characterItemFragment, activity, true)
    }

    private fun getClicks() {
        binding.imageViewRefreshCharacter.setOnClickListener {
            binding.progressBarCharacter.visibility = View.VISIBLE
            refreshList()
        }
    }

    private fun refreshList() {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1500)
            characterViewModel.getInfos()
            withContext(Dispatchers.Main) {
                binding.progressBarCharacter.visibility = View.GONE
            }
        }
    }
}