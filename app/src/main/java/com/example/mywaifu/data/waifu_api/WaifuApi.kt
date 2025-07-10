package com.example.mywaifu.data.waifu_api


import com.example.mywaifu.data.waifu_api.models.ManyWaifuResponse
import com.example.mywaifu.data.waifu_api.models.WaifuResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


data class ExcludeBody(
    val exclude: List<String> = emptyList()
)

interface WaifuApi {

    @GET("{type}/{category}")
    fun getSingleImage(
        @Path("type") type: String, // sfw или nsfw
        @Path("category") category: String // Например, waifu, neko и т.д.
    ): Call<WaifuResponse>

    @POST("many/{type}/{category}")
    fun getManyImage(
        @Path("type") type: String,
        @Path("category") category: String,
        @Body excludeBody: ExcludeBody = ExcludeBody()
    ): Call<ManyWaifuResponse>
}