package com.rodrigoads.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoads.rickandmortyapp.api.ResultInfosApi
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfosViewModel : ViewModel() {
    private var resultInfo = ResultInfosApi()
    var requestRandomPage = 0
    val errorInfoMessage = MutableLiveData<String>()

    fun requestInfo(request: Call<ResultInfosApi>, getObjectInfos: getObjectInfos) {
        request.enqueue(object : Callback<ResultInfosApi> {
            override fun onResponse(
                call: Call<ResultInfosApi>,
                response: Response<ResultInfosApi>
            ) {
                if (response.isSuccessful) {
                    resultInfo = response.body() ?: ResultInfosApi()
                    requestRandomPage = (1..resultInfo.infos.infoPages).random()
                    getObjectInfos.getPage(requestRandomPage)
                }
            }

            override fun onFailure(call: Call<ResultInfosApi>, t: Throwable) {
                errorInfoMessage.postValue(t.message)
            }
        })
    }
}
