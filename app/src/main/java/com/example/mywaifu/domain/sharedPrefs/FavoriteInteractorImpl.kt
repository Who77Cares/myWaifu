package com.example.mywaifu.domain.sharedPrefs

import com.example.mywaifu.Resource

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
): FavoriteInteractor {
    override fun readFavorite(consumer: FavoriteInteractor.FavoriteConsumer) {
        consumer.consume(repository.readFromFavorite().data)
    }

    override fun writeToFavorite(s: String) {
        repository.writeToFavorite(s)
    }

    override fun clearFavorite() {
        repository.clearFavorite()
    }

    override fun validation(s: String): Resource<String> {
        if (s.isBlank()) return Resource.Error("Empty link")

        val current = repository.readFromFavorite().data ?: mutableListOf()

        if (current.contains(s)) return  Resource.Error("Already in favorite")

        if (current.size >= 10) return Resource.Error("Bigger then 10")

        else {
            writeToFavorite(s)
            return Resource.Success("Success")
        }


    }
}