package com.example.mywaifu.data.waifu_api


import com.example.mywaifu.data.waifu_api.models.WaifuResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WaifuApi {

    @GET("{type}/{category}")
    fun getSingleImage(
        @Path("type") type: String, // sfw или nsfw
        @Path("category") category: String // Например, waifu, neko и т.д.
    ): Call<WaifuResponse>
}