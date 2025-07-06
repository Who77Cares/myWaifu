package com.example.mywaifu.ui.favorite

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mywaifu.R
import com.example.mywaifu.data.sharedPrefs.FAVORITE
import com.example.mywaifu.data.sharedPrefs.PrefsStorageClient

class SavedToFavoritesActivity : AppCompatActivity() {

    private lateinit var waifuList: List<String>
    private lateinit var recycleView: RecyclerView
    private lateinit var clearWaifu: Button

    private lateinit var prefsStorageClient: PrefsStorageClient

    private val adapter = WaifuAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_to_favorites)


        recycleView = findViewById(R.id.waifuList)
        clearWaifu = findViewById(R.id.clearWaifu)

        prefsStorageClient = PrefsStorageClient()

        val prefs = getSharedPreferences(FAVORITE, MODE_PRIVATE)
        val favorite = prefsStorageClient.read(prefs)?.toMutableList() ?: mutableListOf()

        waifuList = favorite


        adapter.waifu = waifuList

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = adapter

        clearWaifu.setOnClickListener {
            // Очистка из хранилища
            prefsStorageClient.clear(prefs)

            // Очистка локального списка
            waifuList = emptyList()

            // Обновление адаптера
            adapter.waifu = waifuList
            adapter.notifyDataSetChanged()

        }



    }
}