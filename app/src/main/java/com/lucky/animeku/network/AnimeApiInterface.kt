package com.lucky.animeku.network

import com.lucky.animeku.model.ListAnime
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface AnimeApiInterface {
    @GET("anime")
    fun searchAnime(@Query("q") query: String, @Query("page") page: Int) : Call<ListAnime>

    @GET("top/anime")
    fun topAnime(@Query("page") page: Int = 1) : Call<ListAnime>
}