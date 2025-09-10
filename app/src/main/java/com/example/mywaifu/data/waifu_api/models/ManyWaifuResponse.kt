package com.example.mywaifu.data.waifu_api.models

import com.google.gson.annotations.SerializedName

class ManyWaifuResponse(
    @SerializedName("files") // а тут мы говорим: "Поле files из JSON помещаем сюда" (можно просто manyUrl заменить на files и все будет работать без  @SerializedName("files")
    val manyUrl: List<String>
): Response()