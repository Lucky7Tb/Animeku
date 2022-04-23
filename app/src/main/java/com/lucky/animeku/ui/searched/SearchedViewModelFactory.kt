package com.lucky.animeku.ui.searched

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lucky.animeku.db.AnimeDao
import java.lang.IllegalArgumentException

class SearchedViewModelFactory(private val db: AnimeDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchedViewModel::class.java)) {
            return SearchedViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}