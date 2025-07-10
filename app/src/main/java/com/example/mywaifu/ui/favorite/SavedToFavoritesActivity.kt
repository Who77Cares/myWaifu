package com.example.mywaifu.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywaifu.databinding.ActivitySavedToFavoritesBinding
import com.example.mywaifu.ui.WaifuAdapter

class SavedToFavoritesActivity: AppCompatActivity() {

    private var viewModel: SaveToFavoriteViewModel? = null
    private lateinit var binding: ActivitySavedToFavoritesBinding

    private val adapter = WaifuAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedToFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this, SaveToFavoriteViewModel.getFactory())
            .get(SaveToFavoriteViewModel::class.java)

        viewModel?.observeWaifuList()?.observe(this) { waifuList ->
            adapter.waifu = waifuList
        }

        viewModel?.getWaifuList()

        binding.waifuRecycleView.adapter = adapter
        binding.waifuRecycleView.layoutManager = LinearLayoutManager(this)

        binding.clearWaifu.setOnClickListener {

            viewModel?.clearWaifu()
            adapter.notifyDataSetChanged()
        }
    }

}