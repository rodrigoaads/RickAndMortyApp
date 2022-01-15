package com.rodrigoads.rickandmortyapp.api

import com.rodrigoads.rickandmortyapp.model.api.character.Character
import com.rodrigoads.rickandmortyapp.model.api.episode.Episode
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

const val BASE_URL = "https://rickandmortyapi.com/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    //Get Infos.

    @GET("character")
    fun getInfosCharacter(): Call<ResultInfosApi>

    @GET("location")
    fun getInfosLocation(): Call<ResultInfosApi>

    @GET("episode")
    fun getInfosEpisode(): Call<ResultInfosApi>

    //Get Results.

    @GET("character?")
    fun getCharacter(@Query("page") page: Int): Call<ResultCharacterApi>

    @GET("location?")
    fun getLocation(@Query("page") page: Int): Call<ResultLocationApi>

    @GET("episode?")
    fun getEpisode(@Query("page") page: Int): Call<ResultEpisodeApi>

    //Get Results by url.

    @GET
    fun getEpisodeUrl(@Url url: String): Call<Episode>

    @GET
    fun getCharacterUrl(@Url url: String): Call<Character>

}

object ApiAccess {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}