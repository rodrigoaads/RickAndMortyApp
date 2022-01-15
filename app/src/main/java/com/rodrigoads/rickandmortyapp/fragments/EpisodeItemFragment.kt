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
import com.rodrigoads.rickandmortyapp.databinding.EpisodeItemFragmentBinding
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.character.Character
import com.rodrigoads.rickandmortyapp.model.api.episode.Episode
import com.rodrigoads.rickandmortyapp.viewmodel.GetCharacterViewModel
import com.rodrigoads.rickandmortyapp.viewmodel.GetEpisodeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodeItemFragment : Fragment(), getObjectInfos {
    lateinit var binding: EpisodeItemFragmentBinding
    private val getEpisodeViewModel: GetEpisodeViewModel by activityViewModels()
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
        binding = EpisodeItemFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getEpisodeViewModel.episodeInfos.observe(this, Observer {
            setEpisodeInfos(it)
        })
        getEpisodeViewModel.characterList.observe(this, Observer {
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

    private fun keepRecyclerViewCharacter(characters: List<Character>) {
        binding.recyclerViewCharacterItem.apply {
            adapter = CharacterAdapter(characters, this@EpisodeItemFragment, context)
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun getCharacter(character: Character) {
        getCharacterViewModel.setCharacter(character)
        val characterItemFragment = CharacterItemFragment()
        supportFragment.startFragment(characterItemFragment, activity)
    }

    private fun setEpisodeInfos(episode: Episode) {
        binding.textViewEpisodeItemName.text = episode.episodeName
        (getString(R.string.episode_episode_item) + episode.episodeEpisode).also {
            binding.textViewEpisodeItemEpisode.text = it
        }
        (getString(R.string.episode_airdate_item) + episode.episodeAirDate).also {
            binding.textViewEpisodeItemAirDate.text = it
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1500)
            getEpisodeViewModel.getCharacters()
            withContext(Dispatchers.Main) {
                binding.progressBarCharacterItem.visibility = View.GONE
            }
        }
    }
}