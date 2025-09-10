package com.example.mywaifu.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mywaifu.databinding.FragmentFavoriteWaifuBinding
import com.example.mywaifu.ui.fragments.view_models.FavoriteWaifuViewModel
import com.example.mywaifu.ui.fragments.view_models.OneWaifuViewModel

class FavoriteWaifuFragment: Fragment() {

    private var _binding: FragmentFavoriteWaifuBinding? = null
    private val binding get() = _binding!!

    private var viewModel: FavoriteWaifuViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteWaifuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.observeAddToFavorite()?.observe(viewLifecycleOwner) { urlList ->
            binding.savedText.text = urlList[0]

            for (i in urlList.indices) {
                Glide.with(this)
                    .load(urlList[i])
                    .into(imageViews[i] as ImageView)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}