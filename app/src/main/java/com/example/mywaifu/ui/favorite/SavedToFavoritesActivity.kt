package com.example.mywaifu.ui.favorite

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mywaifu.Creator
import com.example.mywaifu.R
import com.example.mywaifu.domain.sharedPrefs.FavoriteInteractor

class SavedToFavoritesActivity: AppCompatActivity() {


    private lateinit var recycleView: RecyclerView
    private lateinit var clearWaifu: Button
    private val waifuList: MutableList<String> = mutableListOf()

    private val favoriteInteractor by lazy {
        Creator.provideFavoriteInteractor(this)
    }


    private val adapter = WaifuAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_to_favorites)


        recycleView = findViewById(R.id.waifuList)
        clearWaifu = findViewById(R.id.clearWaifu)

        favoriteInteractor.readFavorite(
            object : FavoriteInteractor.FavoriteConsumer {
                override fun consume(favorite: MutableList<String>?) {
                    waifuList.clear()
                    waifuList.addAll(favorite ?: mutableListOf() )
                }

            }
        )



        adapter.waifu = waifuList

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = adapter

        clearWaifu.setOnClickListener {
            // Очистка из хранилища

            favoriteInteractor.clearFavorite()

            // Очистка локального списка
            waifuList.clear()

            // Обновление адаптера
            adapter.waifu = waifuList
            adapter.notifyDataSetChanged()

        }



    }
}