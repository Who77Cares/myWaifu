package com.example.mywaifu.domain.sharedPrefs


import com.example.mywaifu.Resource

interface FavoriteRepository {

    fun writeToFavorite(s: String)
    fun readFromFavorite(): Resource<MutableList<String>>
    fun clearFavorite()

}


