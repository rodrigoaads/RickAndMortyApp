package com.rodrigoads.rickandmortyapp.api

import com.google.gson.annotations.SerializedName
import com.rodrigoads.rickandmortyapp.model.api.infos.Infos
import com.rodrigoads.rickandmortyapp.model.api.location.Location

data class ResultLocationApi(
    @SerializedName("info")
    val infos: Infos,

    @SerializedName("results")
    val locationList: List<Location>,
) {
    constructor() : this(Infos(), listOf())
}