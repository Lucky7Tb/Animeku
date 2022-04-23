package com.lucky.animeku.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucky.animeku.db.AnimeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(private val db: AnimeDao): ViewModel() {
    val favoriteAnimeData = db.getFavoriteAnime()

    fun deleteFavoriteAnime(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.deleteFavoriteAnime(id)
            }
        }
    }
}