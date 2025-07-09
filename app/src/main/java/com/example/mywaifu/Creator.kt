package com.example.mywaifu

import android.content.Context
import com.example.mywaifu.data.sharedPrefs.FAVORITE_WAIFU
import com.example.mywaifu.data.sharedPrefs.FavoriteRepositoryImpl
import com.example.mywaifu.data.sharedPrefs.PrefsStorageClient
import com.example.mywaifu.data.waifu_api.RetrofitNetworkClient
import com.example.mywaifu.data.waifu_api.WaifuRepositoryImpl
import com.example.mywaifu.domain.sharedPrefs.FavoriteInteractor
import com.example.mywaifu.domain.sharedPrefs.FavoriteInteractorImpl
import com.example.mywaifu.domain.sharedPrefs.FavoriteRepository
import com.example.mywaifu.domain.waifu_api.WaifuInteractorImpl
import com.example.mywaifu.domain.waifu_api.WaifuInteractor
import com.example.mywaifu.domain.waifu_api.WaifuRepository
import com.google.gson.reflect.TypeToken

object Creator {

    private fun getWifuRepository(): WaifuRepository {
        return WaifuRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideWifuInteractor(): WaifuInteractor {
        return WaifuInteractorImpl(getWifuRepository())
    }


    private fun getFavoriteRepository(context: Context): FavoriteRepository {
        return FavoriteRepositoryImpl(
            PrefsStorageClient(
                context = context,
                dataKey = "FAVORITE",
                prefsFileName = FAVORITE_WAIFU,
                type = object : TypeToken<MutableList<String>> () {}.type
            )
        )
    }

    fun  provideFavoriteInteractor(context: Context): FavoriteInteractor {
        return FavoriteInteractorImpl(getFavoriteRepository(context))
    }

}