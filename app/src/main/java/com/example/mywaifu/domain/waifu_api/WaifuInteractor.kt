package com.example.mywaifu.domain.waifu_api

interface WaifuInteractor {

    fun getWaifu(type: String, category: String, singleImg: Boolean, consumer: WaifuConsumer)


    interface WaifuConsumer {
        fun consume(data: Any?, errorMessage: String?)
    }

}