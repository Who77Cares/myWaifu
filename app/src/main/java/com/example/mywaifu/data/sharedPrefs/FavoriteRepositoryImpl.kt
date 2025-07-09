package com.example.mywaifu.data.sharedPrefs

import com.example.mywaifu.Resource
import com.example.mywaifu.data.sharedPrefs.client.StorageClient
import com.example.mywaifu.domain.sharedPrefs.FavoriteRepository

class FavoriteRepositoryImpl(
    private val storageClient: StorageClient<MutableList<String>>
): FavoriteRepository {

    override fun writeToFavorite(s: String) {
        val favoriteWaifu = storageClient.read() ?: mutableListOf()
        favoriteWaifu.add(0, s)
        storageClient.write(favoriteWaifu)
    }

    override fun readFromFavorite(): Resource<MutableList<String>> {
        val waifu = storageClient.read() ?: mutableListOf()
        return Resource.Success(waifu)
    }

    override fun clearFavorite() {
        storageClient.clear()
    }
}