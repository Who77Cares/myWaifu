package com.example.mywaifu.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.mywaifu.Resource
import com.example.mywaifu.domain.waifu_api.WaifuInteractor
import com.example.mywaifu.ui.favorite.SavedToFavoritesActivity

class MainActivity: AppCompatActivity() {

    private var imgUrl: String = ""
    private lateinit var imageView: ImageView
    private lateinit var getWaifu: Button
    private lateinit var getWaifu2: Button
    private lateinit var getWaifu3: Button
    private lateinit var addToFavorite: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var savedText: TextView
    private lateinit var goToWaifu: Button



    private val waifuInteractor = Creator.provideWifuInteractor()
    private val handler = Handler(Looper.getMainLooper())

    private val favoriteInteractor by lazy {
        Creator.provideFavoriteInteractor(this)
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
            val result = favoriteInteractor.validation(imgUrl)

            when (result) {
                is Resource.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Success -> {
                    Toast.makeText(this, result.data , Toast.LENGTH_SHORT).show()
                    savedText.text = imgUrl
                }
            }
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
}