package com.lucky.animeku.ui.searched

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucky.animeku.db.AnimeDao
import com.lucky.animeku.db.AnimeEntity
import com.lucky.animeku.model.DataAnime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchedViewModel(private val db: AnimeDao): ViewModel() {

    fun addToFavorite(anime: DataAnime) {
        val dataAnime  = AnimeEntity(
            mal_id = anime.malId,
            title = anime.title,
            image = anime.images.jpg.large_image_url!!,
            rank = anime.rank,
            score = anime.score
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insertFavoriteAnime(dataAnime)
            }
        }
    }
}