package com.example.mywaifu.ui.main
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mywaifu.R
import com.example.mywaifu.databinding.ActivityMainBinding
import com.example.mywaifu.ui.WaifuAdapter
import com.example.mywaifu.ui.favorite.SavedToFavoritesActivity
import com.example.mywaifu.ui.fragments.OneWaifuFragment

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


            if (savedInstanceState == null) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.rootFragmentContainerView, OneWaifuFragment())
                    .commit()
            }
        }

}