package com.example.mywaifu.data.waifu_api


import com.example.mywaifu.Resource
import com.example.mywaifu.data.waifu_api.client.NetworkClient
import com.example.mywaifu.data.waifu_api.models.WaifuRequest
import com.example.mywaifu.data.waifu_api.models.WaifuResponse
import com.example.mywaifu.domain.waifu_api.WaifuRepository

class WaifuRepositoryImpl(private val networkClient: NetworkClient): WaifuRepository {

    override fun getWaifu(type: String, category: String): Resource<String> {

        val request = WaifuRequest(type, category)
        val response = networkClient.doRequest(request)

        return when (response.resultCode) {

            -1 -> Resource.Error("Connection error")

            200 -> {
                val waifuResponse = response as WaifuResponse
                val result = waifuResponse.url

                if(result.isEmpty()) {
                    Resource.Error("Nothing is found")

                } else {
                    Resource.Success(result)
                }
            }
            else -> {
                Resource.Error("Server error")
            }
        }
    }
}

