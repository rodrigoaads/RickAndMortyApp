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
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigoads.rickandmortyapp.adapter.EpisodeAdapter
import com.rodrigoads.rickandmortyapp.databinding.EpisodeFragmentBinding
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.episode.Episode
import com.rodrigoads.rickandmortyapp.viewmodel.EpisodeViewModel
import com.rodrigoads.rickandmortyapp.viewmodel.GetEpisodeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodeFragment : Fragment(), getObjectInfos {
    lateinit var binding: EpisodeFragmentBinding
    private val episodeViewModel: EpisodeViewModel by activityViewModels()
    private val getEpisodeViewModel: GetEpisodeViewModel by activityViewModels()
    private val supportFragment = SupportFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EpisodeFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodeViewModel.resultEpisodeApi.observe(this, Observer {
            keepRecyclerViewEpisode(it.episode)
        })

        binding.progressBarEpisode.visibility = View.GONE
        getClicks()
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun keepRecyclerViewEpisode(episodeList: List<Episode>) {
        binding.recyclerViewEpisode.apply {
            adapter = EpisodeAdapter(episodeList, this@EpisodeFragment)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getEpisode(episode: Episode) {
        getEpisodeViewModel.setEpisode(episode)
        val episodeItemFragment = EpisodeItemFragment()
        supportFragment.startFragment(episodeItemFragment, activity, true)
    }

    private fun getClicks() {
        binding.imageViewRefreshEpisode.setOnClickListener {
            binding.progressBarEpisode.visibility = View.VISIBLE
            refreshList()
        }
    }

    private fun refreshList() {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1500)
            episodeViewModel.getInfos()
            withContext(Dispatchers.Main) {
                binding.progressBarEpisode.visibility = View.GONE
            }
        }
    }
}