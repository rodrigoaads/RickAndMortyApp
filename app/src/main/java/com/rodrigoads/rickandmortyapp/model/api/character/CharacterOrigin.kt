package com.rodrigoads.rickandmortyapp.model.api.character

import com.google.gson.annotations.SerializedName

data class CharacterOrigin(
    @SerializedName("name")
    val originName: String,

    @SerializedName("url")
    val originUrl: String
) {
    constructor() : this("", "")
}
