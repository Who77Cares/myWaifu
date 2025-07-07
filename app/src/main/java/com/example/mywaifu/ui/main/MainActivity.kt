package com.example.mywaifu.ui.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mywaifu.Creator
import com.example.mywaifu.R

import com.example.mywaifu.data.waifuAPI.WaifuApi
import com.example.mywaifu.data.waifuAPI.models.WaifuResponse
import com.example.mywaifu.data.sharedPrefs.FAVORITE
import com.example.mywaifu.data.sharedPrefs.PrefsStorageClient
import com.example.mywaifu.domain.api.WaifuInteractor
import com.example.mywaifu.ui.favorite.SavedToFavoritesActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var imgUrl: String = ""
    private lateinit var imageView: ImageView
    private lateinit var getWaifu: Button
    private lateinit var getWaifu2: Button
    private lateinit var getWaifu3: Button
    private lateinit var addToFavorite: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var savedText: TextView
    private lateinit var goToWaifu: Button

    private lateinit var prefsStorageClient: PrefsStorageClient

    private val waifuInteractor = Creator.provideWifuInteractor()
    private var handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefsStorageClient = PrefsStorageClient()

        imageView = findViewById(R.id.imageView)
        getWaifu = findViewById(R.id.getWaifu)
        getWaifu2 = findViewById(R.id.getWaifu2)
        getWaifu3 = findViewById(R.id.getWaifu3)
        progressBar = findViewById(R.id.progressBar)
        savedText = findViewById(R.id.savedText)
        addToFavorite = findViewById(R.id.addToFavoriteButton)
        goToWaifu = findViewById(R.id.goToFavoriteButton)

        getWaifu.setOnClickListener {
            getMyWaifu("sfw", "dance")
        }
        getWaifu2.setOnClickListener {
            getMyWaifu("sfw", "awoo")
        }

        getWaifu3.setOnClickListener {
            getMyWaifu("sfw", "cringe")
        }

        addToFavorite.setOnClickListener {
            addToFavorite()
        }

        goToWaifu.setOnClickListener {
            val intent = Intent(this, SavedToFavoritesActivity::class.java)
            startActivity(intent)

        }


    }

    private fun getMyWaifu(type: String, category: String) {

        progressBar.visibility = View.VISIBLE

        waifuInteractor.getWaifu(
            type = type,
            category = category,
            object : WaifuInteractor.WaifuConsumer {

                override fun consume(url: String?, errorMessage: String?) {
                    handler.post {
                        progressBar.visibility = View.INVISIBLE

                        url?.let {
                            imgUrl = it
                            Glide.with(this@MainActivity)
                                .load(url)
                                .into(imageView)
                        }
                    }


                }

            }
        )
    }


    private fun addToFavorite() {
        val prefs = getSharedPreferences(FAVORITE, MODE_PRIVATE)

        val favorite = prefsStorageClient.read(prefs)?.toMutableList() ?: mutableListOf()

        if (favorite.contains(imgUrl)) {
            Toast.makeText(this, "Уже есть", Toast.LENGTH_LONG).show()
            return
        }

        if (imgUrl.isBlank()) {
            Toast.makeText(this, "Нет картинки для добавления", Toast.LENGTH_SHORT).show()
            return
        }

        if (favorite.size > 10) {
            Toast.makeText(this, "Не-не больше 10", Toast.LENGTH_LONG).show()
            return
        }

        favorite.add(imgUrl)

        prefsStorageClient.write(prefs, favorite, FAVORITE)

        savedText.setText(favorite.toString())
    }
}