package com.rodrigoads.rickandmortyapp.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigoads.rickandmortyapp.R
import com.rodrigoads.rickandmortyapp.adapter.EpisodeAdapter
import com.rodrigoads.rickandmortyapp.databinding.CharacterItemFragmentBinding
import com.rodrigoads.rickandmortyapp.glide.GlideManager
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.character.Character
import com.rodrigoads.rickandmortyapp.model.api.episode.Episode
import com.rodrigoads.rickandmortyapp.viewmodel.GetCharacterViewModel
import com.rodrigoads.rickandmortyapp.viewmodel.GetEpisodeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterItemFragment : Fragment(), getObjectInfos {
    private val getCharacterViewModel: GetCharacterViewModel by activityViewModels()
    private val getEpisodeViewModel: GetEpisodeViewModel by activityViewModels()
    private val supportFragment = SupportFragment()

    lateinit var binding: CharacterItemFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterItemFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getCharacterViewModel.characterInfos.observe(this, Observer {
            setCharacterInfos(it)
        })
        getCharacterViewModel.episodeList.observe(this, Observer {
            keepRecyclerViewEpisode(it.sortedBy { it.episodeId })
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

    private fun keepRecyclerViewEpisode(episodeList: List<Episode>) {
        binding.recyclerViewEpisodeItem.apply {
            adapter = EpisodeAdapter(episodeList, this@CharacterItemFragment)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getEpisode(episode: Episode) {
        getEpisodeViewModel.setEpisode(episode)
        val episodeItemFragment = EpisodeItemFragment()
        supportFragment.startFragment(episodeItemFragment, activity)
    }

    private fun setCharacterInfos(character: Character) {
        binding.textViewCharacterItemName.text = character.characterName
        (getString(R.string.character_gender_item) + character.characterGender).also {
            binding.textViewCharacterItemGender.text = it
        }
        (getString(R.string.character_origin_item) + character.characterOrigin.originName).also {
            binding.textViewCharacterItemOrigin.text = it
        }
        (getString(R.string.character_specie_item) + character.characterSpecies).also {
            binding.textViewCharacterItemSpecie.text = it
        }
        (getString(R.string.character_status_item) + character.characterStatus).also {
            binding.textViewCharacterItemStatus.text = it
        }

        when (character.characterStatus) {
            "Alive" -> {
                binding.imageViewStatus.setImageResource(R.drawable.ic_baseline_circle_alive)
            }
            "Dead" -> {
                binding.imageViewStatus.setImageResource(R.drawable.ic_baseline_circle_dead)
            }
            else -> {
                binding.imageViewStatus.setImageResource(R.drawable.ic_baseline_circle_unknown)
            }
        }

        GlideManager().setImage(binding.imageViewCharacterItem, character.characterImage)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1500)
            getCharacterViewModel.getEpisodes()
            withContext(Dispatchers.Main) {
                binding.progressBarEpisodeItem.visibility = View.GONE
            }
        }
    }
}