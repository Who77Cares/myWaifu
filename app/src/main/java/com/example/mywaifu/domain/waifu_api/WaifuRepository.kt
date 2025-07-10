package com.example.mywaifu.domain.waifu_api

import com.example.mywaifu.Resource

interface WaifuRepository {

    fun getWaifu(type: String, category: String): Resource<String>
    fun getManyWaifu(type: String, category: String): Resource<List<String>>

}