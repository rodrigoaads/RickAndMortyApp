package com.rodrigoads.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoads.rickandmortyapp.api.ApiAccess
import com.rodrigoads.rickandmortyapp.api.ResultLocationApi
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.character.Character
import com.rodrigoads.rickandmortyapp.model.api.location.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationViewModel : ViewModel(), getObjectInfos {
    val resultLocationApi = MutableLiveData<ResultLocationApi>()
    val errorMessageResultLocation = MutableLiveData<String>()

    private val infoViewModel = InfosViewModel()
    private var lastPageReceived = 0

    fun getInfos() {
        val request = ApiAccess.retrofitService.getInfosLocation()
        infoViewModel.requestInfo(request, this)
    }

    override fun getPage(page: Int) {
        if (page == lastPageReceived) {
            getInfos()
        } else {
            lastPageReceived = page
            getLocation(page)
        }
    }

    fun getLocation(page: Int) {
        val serviceResult = ApiAccess.retrofitService.getLocation(page)
        serviceResult.enqueue(object : Callback<ResultLocationApi> {
            override fun onResponse(
                call: Call<ResultLocationApi>,
                response: Response<ResultLocationApi>
            ) {
                if (response.isSuccessful) {
                    resultLocationApi.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ResultLocationApi>, t: Throwable) {
                errorMessageResultLocation.postValue(t.message)
            }

        })
    }
}

class GetLocationViewModel() : ViewModel() {
    val locationInfos = MutableLiveData<Location>()
    val residentsList = MutableLiveData<MutableList<Character>>()
    val errorMessageSetResidentItem = MutableLiveData<String>()

    private var list: MutableList<Character> = mutableListOf()

    fun setLocation(location: Location) {
        residentsList.value?.clear()
        locationInfos.value = location
        val list: MutableList<Character> = mutableListOf()
        location.locationResidents.forEach {
            getResidentsItem(it, list)
            this.list = list
        }
    }

    fun getResidents() {
        residentsList.postValue(list)
    }

    fun getResidentsItem(resident: String, list: MutableList<Character>) {
        val serviceResult = ApiAccess.retrofitService.getCharacterUrl(resident)
        serviceResult.enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if (response.isSuccessful) {
                    val characterResponse = response.body() ?: Character()
                    list.add(characterResponse)
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                errorMessageSetResidentItem.postValue(t.message)
            }

        })
    }
}