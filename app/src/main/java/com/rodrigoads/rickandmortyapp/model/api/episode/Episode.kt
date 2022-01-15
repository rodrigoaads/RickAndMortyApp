package com.rodrigoads.rickandmortyapp.model.api.episode

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("id")
    val episodeId: Int,

    @SerializedName("name")
    val episodeName: String,

    @SerializedName("air_date")
    val episodeAirDate: String,

    @SerializedName("episode")
    val episodeEpisode: String,

    @SerializedName("characters")
    val episodeCharacters: ArrayList<String>,

    @SerializedName("url")
    val episodeUrl: String,

    @SerializedName("created")
    val episodeCreated: String,
) {
    constructor() : this(0, "", "", "", arrayListOf(), "", "")
}