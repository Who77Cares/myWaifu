package com.example.mywaifu.domain.sharedPrefs

import com.example.mywaifu.Resource

interface FavoriteInteractor {

    fun readFavorite(consumer: FavoriteConsumer)
    fun writeToFavorite(s: String)
    fun clearFavorite()

    fun validation(s: String): Resource<String>

    interface FavoriteConsumer {
        fun consume(favorite: MutableList<String>?)
    }
}