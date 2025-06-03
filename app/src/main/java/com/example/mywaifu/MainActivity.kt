package com.example.mywaifu

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private var imgUrl: String? = null
    private lateinit var imageView: ImageView
    private lateinit var getWaifu: Button
    private lateinit var getWaifu2: Button
    private lateinit var getWaifu3: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var savedText: TextView

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

        imageView = findViewById(R.id.imageView)
        getWaifu = findViewById(R.id.getWaifu)
        getWaifu2 = findViewById(R.id.getWaifu2)
        getWaifu3 = findViewById(R.id.getWaifu3)
        progressBar = findViewById(R.id.progressBar)
        savedText = findViewById(R.id.savedText)

        getWaifu.setOnClickListener {
            getMyWaifu("sfw", "dance")
        }
        getWaifu2.setOnClickListener {
            getMyWaifu("sfw", "awoo")
        }

        getWaifu3.setOnClickListener {
            getMyWaifu("sfw", "cringe")
        }



    }

    private fun getMyWaifu(type: String, category: String) {

        progressBar.visibility = View.VISIBLE

        val call = RetrofitClient.waifuApiService.getSingleImage(type, category)
        call.enqueue(object : Callback<WaifuResponse> {
            override fun onResponse(call: Call<WaifuResponse>, response: Response<WaifuResponse>) {

                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    // Получаем URL изображения
                    imgUrl = response.body()?.url
                    Log.d(TAG, "Image URL: $imgUrl")

                    // Загружаем изображение в ImageView
                    imgUrl?.let { url ->
                        Glide.with(this@MainActivity)
                            .load(url)
                            .into(imageView)
                    }

                    savedText.text = imgUrl?.substringAfterLast('/')


                } else {
                    Log.d(TAG, "Ошибка: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WaifuResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.d(TAG, "Ошибка сети: ${t.message}")
            }
        })
    }
}