package com.example.mywaifu.data.waifu_api.client

import com.example.mywaifu.data.waifu_api.models.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}