package com.example.mywaifu

import com.example.mywaifu.data.waifuAPI.RetrofitNetworkClient
import com.example.mywaifu.data.waifuAPI.WaifuRepositoryImpl
import com.example.mywaifu.domain.WaifuInteractorImpl
import com.example.mywaifu.domain.api.WaifuInteractor
import com.example.mywaifu.domain.api.WaifuRepository

object Creator {

    private fun getWifuRepository(): WaifuRepository {
        return WaifuRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideWifuInteractor(): WaifuInteractor {
        return WaifuInteractorImpl(getWifuRepository())
    }

}