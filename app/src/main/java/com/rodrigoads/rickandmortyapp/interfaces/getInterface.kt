package com.rodrigoads.rickandmortyapp.interfaces

import com.rodrigoads.rickandmortyapp.model.api.character.Character
import com.rodrigoads.rickandmortyapp.model.api.episode.Episode
import com.rodrigoads.rickandmortyapp.model.api.location.Location

interface getObjectInfos {
    fun getPage(page: Int) {}

    fun getCharacter(character: Character) {}

    fun getLocation(location: Location) {}

    fun getEpisode(episode: Episode) {}
}