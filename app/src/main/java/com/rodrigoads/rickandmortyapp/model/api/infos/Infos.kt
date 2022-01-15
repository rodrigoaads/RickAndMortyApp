package com.rodrigoads.rickandmortyapp.model.api.infos

import com.google.gson.annotations.SerializedName

data class Infos(
    @SerializedName("count")
    val infoCount: Int,

    @SerializedName("pages")
    val infoPages: Int,

    @SerializedName("next")
    val infoNext: String?,

    @SerializedName("prev")
    val infoPrev: String?,
) {
    constructor() : this(0, 0, null, null)
}
