package com.lucky.animeku.model

data class DataAnime(
    val rating: String,
    val title: String,
    val score: Double,
    val rank: Int,
    val episodes: Int,
    val malId: Int,
    val synopsis: String? = null,
    val images: Images,
)

data class Images(
    val jpg: Jpg,
)

data class Jpg(
    val large_image_url: String? = null,
    val small_image_url: String? = null,
    val image_url: String? = null
)