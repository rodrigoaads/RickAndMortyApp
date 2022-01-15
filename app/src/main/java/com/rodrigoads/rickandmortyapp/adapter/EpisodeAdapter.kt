package com.rodrigoads.rickandmortyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoads.rickandmortyapp.databinding.EpisodeItemListBinding
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.episode.Episode

class EpisodeAdapter(
    val list: List<Episode>,
    val getObjectInfos: getObjectInfos
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding =
            EpisodeItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding, getObjectInfos)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class EpisodeViewHolder(
        private val episodeItemListBinding: EpisodeItemListBinding,
        private val getObjectInfos: getObjectInfos
    ) : RecyclerView.ViewHolder(episodeItemListBinding.root) {
        fun bind(episode: Episode) {
            episodeItemListBinding.textViewEpisodeName.text = episode.episodeName
            episodeItemListBinding.textViewEpisodeAirDate.text = episode.episodeAirDate

            episodeItemListBinding.root.setOnClickListener {
                getObjectInfos.getEpisode(episode)
            }
        }
    }
}