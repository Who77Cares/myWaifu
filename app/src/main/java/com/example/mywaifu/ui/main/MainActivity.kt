package com.example.mywaifu.ui.main
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mywaifu.databinding.ActivityMainBinding
import com.example.mywaifu.ui.WaifuAdapter
import com.example.mywaifu.ui.favorite.SavedToFavoritesActivity

class MainActivity: AppCompatActivity() {

    private var viewModel: MainViewModel? = null
    private lateinit var binding: ActivityMainBinding

    private val adapter = WaifuAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageViews = listOf(
            binding.imageViewSaved1,
            binding.imageViewSaved2,
            binding.imageViewSaved3
        )


        viewModel = ViewModelProvider(this, MainViewModel.getFactory())
            .get(MainViewModel::class.java)

        viewModel?.observeAddToFavorite()?.observe(this) { urlList ->
            binding.savedText.text = urlList[0]

            for (i in urlList.indices) {
                Glide.with(this)
                    .load(urlList[i])
                    .into(imageViews[i])

            }
        }


        viewModel?.observeToast()?.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


        viewModel?.observeState()?.observe(this) {
            render(it)
        }




        binding.getWaifu1.setOnClickListener {
            viewModel?.getMyWaifu("sfw", "dance", true)
        }

        binding.getWaifu2.setOnClickListener {
            viewModel?.getMyWaifu("sfw", "awoo", true)
        }

        binding.getWaifu3.setOnClickListener {
            viewModel?.getMyWaifu("sfw", "cringe", false)
        }

        binding.addToFavoriteButton.setOnClickListener {
           viewModel?.addToFavorite()

//            Glide.with(this)
//                .load(url)
//                .into(binding.imageViewSaved1)
        }

        binding.goToFavoriteButton.setOnClickListener {
            val intent = Intent(this, SavedToFavoritesActivity::class.java)
            startActivity(intent)
        }

        binding.manyWaifuRecycleView.adapter = adapter
        binding.manyWaifuRecycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }


    private fun render(state: MainState) {
        when (state) {
            is MainState.Content -> showContent(state.url)
            is MainState.Error -> showError(state.message)
            is MainState.ManyContent -> showManuContent(state.manyUrl)
            MainState.Loading -> showLading()

        }
    }



    private fun showContent(url: String) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.manyWaifuRecycleView.visibility = View.INVISIBLE
        binding.imageView.visibility = View.VISIBLE

        Glide.with(this)
            .load(url)
            .into(binding.imageView)
    }


    private fun showError(message: String) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.imageView.visibility = View.INVISIBLE
        binding.manyWaifuRecycleView.visibility = View.INVISIBLE

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun showLading() {
        binding.imageView.visibility = View.INVISIBLE
        binding.manyWaifuRecycleView.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }
    

    private fun showManuContent(manyUrl: List<String>) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.imageView.visibility = View.INVISIBLE
        binding.manyWaifuRecycleView.visibility = View.VISIBLE

        adapter.waifu = manyUrl
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Много контента пришело", Toast.LENGTH_LONG).show()
    }

}