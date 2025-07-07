package com.example.mywaifu.data.waifu_api

import com.example.mywaifu.data.waifu_api.client.NetworkClient
import com.example.mywaifu.data.waifu_api.models.Response
import com.example.mywaifu.data.waifu_api.models.WaifuRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient() : NetworkClient {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.waifu.pics/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val waifuService = retrofit.create(WaifuApi::class.java)


    override fun doRequest(dto: Any): Response {

        if (dto is WaifuRequest) {

            val call = waifuService.getSingleImage(dto.type, dto.category)
            val resp = call.execute()
            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }

    }
}