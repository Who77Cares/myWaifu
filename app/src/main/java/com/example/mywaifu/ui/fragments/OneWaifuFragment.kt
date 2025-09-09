package com.example.mywaifu.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

import com.example.mywaifu.databinding.FragmentOneWaifuBinding
import com.example.mywaifu.ui.WaifuAdapter
import com.example.mywaifu.ui.main.MainState

import com.example.mywaifu.ui.main.MainViewModel

class OneWaifuFragment: Fragment() {

    private var viewModel: MainViewModel? = null
    private var _binding: FragmentOneWaifuBinding? = null
    private val binding get() = _binding!!

    private val adapter: WaifuAdapter = WaifuAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneWaifuBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val imageViews = listOf<View>(
            binding.imageViewSaved1,
            binding.imageViewSaved2,
            binding.imageViewSaved3
        )



        viewModel = ViewModelProvider(this, MainViewModel.getFactory())
            .get(MainViewModel::class.java)

        viewModel?.observeAddToFavorite()?.observe(viewLifecycleOwner) { urlList ->
            binding.savedText.text = urlList[0]

            for (i in urlList.indices) {
                Glide.with(this)
                    .load(urlList[i])
                    .into(imageViews[i] as ImageView)
            }

            viewModel?.observeToast()?.observe(viewLifecycleOwner) { message ->

                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

            viewModel?.observeState()?.observe(viewLifecycleOwner) {
                render(it)
            }

            binding.getWaifu1.setOnClickListener {
                viewModel?.getMyWaifu("sfw", "dance", true)
            }

            binding.getWaifu2.setOnClickListener {
                viewModel?.getMyWaifu("sfw", "awoo", true)
            }


            binding.getWaifu3.setOnClickListener {
                viewModel?.getMyWaifu("nsfw", "waifu", false)
            }


            binding.addToFavoriteButton.setOnClickListener {
                viewModel?.addToFavorite()
            }

            binding.addToFavoriteButton.setOnClickListener {
                viewModel?.addToFavorite()
            }

            binding.goToFavoriteButton.setOnClickListener {
                // навигируемся через фрагменты
//                val intent = Intent(this, SavedToFavoritesActivity::class.java)
//                startActivity(intent)
            }

        }

        binding.manyWaifuRecycleView.adapter = adapter
        binding.manyWaifuRecycleView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

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

        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

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

        Toast.makeText(requireContext(), "Много контента пришело", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}