package com.rodrigoads.rickandmortyapp.api

import com.google.gson.annotations.SerializedName
import com.rodrigoads.rickandmortyapp.model.api.character.Character
import com.rodrigoads.rickandmortyapp.model.api.infos.Infos

data class ResultCharacterApi(
    @SerializedName("info")
    val infos: Infos,

    @SerializedName("results")
    val characterList: List<Character>
) {
    constructor() : this(Infos(), listOf())
}
