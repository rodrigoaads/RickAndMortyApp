package com.rodrigoads.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoads.rickandmortyapp.api.ApiAccess
import com.rodrigoads.rickandmortyapp.api.ResultCharacterApi
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.character.Character
import com.rodrigoads.rickandmortyapp.model.api.episode.Episode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel(), getObjectInfos {

    val resultCharacterApi = MutableLiveData<ResultCharacterApi>()
    val errorMessageResultCharacter = MutableLiveData<String>()

    private val infoViewModel = InfosViewModel()
    private var lastPageReceived = 0


    fun getInfos() {
        val request = ApiAccess.retrofitService.getInfosCharacter()
        infoViewModel.requestInfo(request, this)
    }

    override fun getPage(page: Int) {
        if (page == lastPageReceived) {
            getInfos()
        } else {
            lastPageReceived = page
            getCharacter(lastPageReceived)
        }
    }

    fun getCharacter(page: Int) {
        val serviceResult = ApiAccess.retrofitService.getCharacter(page)
        serviceResult.enqueue(object : Callback<ResultCharacterApi> {
            override fun onResponse(
                call: Call<ResultCharacterApi>,
                response: Response<ResultCharacterApi>
            ) {
                if (response.isSuccessful) {
                    resultCharacterApi.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ResultCharacterApi>, t: Throwable) {
                errorMessageResultCharacter.postValue(t.message)
            }

        })
    }
}

class GetCharacterViewModel : ViewModel() {

    val characterInfos = MutableLiveData<Character>()
    val episodeList = MutableLiveData<MutableList<Episode>>()
    val errorMessageGetEpisodeItem = MutableLiveData<String>()

    private var list: MutableList<Episode> = mutableListOf()

    fun setCharacter(character: Character) {
        episodeList.value?.clear()
        characterInfos.value = character
        val list: MutableList<Episode> = mutableListOf()
        character.characterEpisode.forEach {
            getEpisodeItem(it, list)
            this.list = list
        }
    }

    fun getEpisodes() {
        episodeList.postValue(list)
    }

    fun getEpisodeItem(episode: String, list: MutableList<Episode>) {
        val serviceResult = ApiAccess.retrofitService.getEpisodeUrl(episode)
        serviceResult.enqueue(object : Callback<Episode> {
            override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                if (response.isSuccessful) {
                    val episodeResponse = response.body() ?: Episode()
                    list.add(episodeResponse)
                }
            }

            override fun onFailure(call: Call<Episode>, t: Throwable) {
                errorMessageGetEpisodeItem.postValue(t.message)
            }

        })
    }
}