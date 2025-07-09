package com.example.mywaifu.ui.main
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mywaifu.databinding.ActivityMainBinding
import com.example.mywaifu.ui.favorite.SavedToFavoritesActivity

class MainActivity: AppCompatActivity() {

    private var viewModel: MainViewModel? = null
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this, MainViewModel.getFactory())
            .get(MainViewModel::class.java)

        viewModel?.observeAddToFavorite()?.observe(this) { url ->
            binding.savedText.text = url
        }


        viewModel?.observeToast()?.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


        viewModel?.observeState()?.observe(this) {
            render(it)
        }




        binding.getWaifu.setOnClickListener {
            viewModel?.getMyWaifu("sfw", "dance")
        }

        binding.getWaifu2.setOnClickListener {
            viewModel?.getMyWaifu("sfw", "awoo")
        }

        binding.getWaifu3.setOnClickListener {
            viewModel?.getMyWaifu("sfw", "cringe")
        }

        binding.addToFavoriteButton.setOnClickListener {
           viewModel?.addToFavorite()
        }

        binding.goToFavoriteButton.setOnClickListener {
            val intent = Intent(this, SavedToFavoritesActivity::class.java)
            startActivity(intent)
        }

    }


    private fun render(state: MainState) {
        when (state) {
            is MainState.Content -> showContent(state.url)
            is MainState.Error -> showError(state.message)
            MainState.Loading -> showLading()
        }
    }



    private fun showContent(url: String) {
        binding.progressBar.visibility = View.INVISIBLE

        Glide.with(this)
            .load(url)
            .into(binding.imageView)
    }


    private fun showError(message: String) {
        binding.progressBar.visibility = View.INVISIBLE

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun showLading() {
        binding.progressBar.visibility = View.VISIBLE
    }

}