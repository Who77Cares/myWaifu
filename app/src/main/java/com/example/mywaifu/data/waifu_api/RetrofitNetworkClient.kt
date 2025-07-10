package com.example.mywaifu.data.waifu_api

import com.example.mywaifu.data.waifu_api.client.NetworkClient
import com.example.mywaifu.data.waifu_api.models.ManyWaifuRequest
import com.example.mywaifu.data.waifu_api.models.Response
import com.example.mywaifu.data.waifu_api.models.WaifuRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitNetworkClient: NetworkClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.waifu.pics/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val waifuService = retrofit.create(WaifuApi::class.java)


    override fun doRequest(dto: Any): Response {
        return when (dto) {
            is WaifuRequest -> makeRequest( waifuService.getSingleImage(dto.type, dto.category) )
            is ManyWaifuRequest -> makeRequest( waifuService.getManyImage(dto.type, dto.category))
            else -> Response().apply { resultCode = 43300 }
        }

    }

    private fun makeRequest(call: Call<out Response>): Response {
        try {
            val response = call.execute()
            val body = response.body() ?: Response()
            return body.apply { resultCode = response.code() }
        } catch (e: IOException) {
            return Response().apply { resultCode = -1 }
        }
    }



}