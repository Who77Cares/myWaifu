package com.example.mywaifu


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface WaifuApi {

    @POST("/nsfw/waifu")
    fun getSingleImage(
        @Path("type") type: String, // sfw или nsfw
        @Path("category") category: String // Например, waifu, neko и т.д.
    ): Call<WaifuResponse>
}