package com.rodrigoads.rickandmortyapp.api

import com.google.gson.annotations.SerializedName
import com.rodrigoads.rickandmortyapp.model.api.infos.Infos

data class ResultInfosApi(
    @SerializedName("info")
    val infos: Infos
) {
    constructor() : this(Infos())
}
