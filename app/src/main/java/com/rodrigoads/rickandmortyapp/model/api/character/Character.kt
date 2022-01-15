package com.rodrigoads.rickandmortyapp.model.api.character

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val characterId: Int,

    @SerializedName("name")
    val characterName: String,

    @SerializedName("status")
    val characterStatus: String,

    @SerializedName("species")
    val characterSpecies: String,

    @SerializedName("type")
    val characterType: String,

    @SerializedName("gender")
    val characterGender: String,

    @SerializedName("origin")
    val characterOrigin: CharacterOrigin,

    @SerializedName("location")
    val characterLocation: CharacterLocation,

    @SerializedName("image")
    val characterImage: String,

    @SerializedName("episode")
    val characterEpisode: ArrayList<String>,

    @SerializedName("url")
    val characterUrl: String,

    @SerializedName("created")
    val characterCreated: String,
) {
    constructor() : this(
        0, "", ",", "", "", "",
        CharacterOrigin(), CharacterLocation(), "", arrayListOf(), "", ""
    )
}
