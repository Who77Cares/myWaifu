package com.example.mywaifu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var imgUrl: String

    object RetrofitClient {
        private const val BASE_URL = "https://api.waifu.pics/"
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val waifuApiService: WaifuApi by lazy {
            retrofit.create(WaifuApi::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


    private fun getMyWaifu(type: String, category: String) {
        val call = RetrofitClient.waifuApiService.getSingleImage(type, category)
        call.enqueue(object: Callback<WaifuResponse>{
            override fun onResponse(p0: Call<WaifuResponse>, response: Response<WaifuResponse>) {
                if (response.isSuccessful) imgUrl = response.body()!!.url
            }

            override fun onFailure(p0: Call<WaifuResponse>, p1: Throwable) {
                println("Ошибка сети")
            }

        })

    }
}