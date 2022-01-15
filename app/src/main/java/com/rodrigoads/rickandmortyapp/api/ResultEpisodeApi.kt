package com.rodrigoads.rickandmortyapp.api

import com.google.gson.annotations.SerializedName
import com.rodrigoads.rickandmortyapp.model.api.episode.Episode
import com.rodrigoads.rickandmortyapp.model.api.infos.Infos

data class ResultEpisodeApi(
    @SerializedName("info")
    val infos: Infos,

    @SerializedName("results")
    val episode: List<Episode>,
) {
    constructor() : this(Infos(), listOf())
}
