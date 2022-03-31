package com.lucky.animeku.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    val baseUrl: String = "https://api.jikan.moe/v4/";
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}