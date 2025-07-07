package com.example.mywaifu.domain.waifu_api

interface WaifuInteractor {

    fun getWaifu(type: String, category: String, consumer: WaifuConsumer)


    interface WaifuConsumer {
        fun consume(url: String?, errorMessage: String?)
    }

}