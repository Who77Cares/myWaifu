package com.example.mywaifu.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mywaifu.databinding.FragmentFavoriteWaifuBinding
import com.example.mywaifu.ui.WaifuAdapter
import com.example.mywaifu.ui.fragments.view_models.FavoriteWaifuViewModel
import com.example.mywaifu.ui.fragments.view_models.OneWaifuViewModel

class FavoriteWaifuFragment: Fragment() {

    private var _binding: FragmentFavoriteWaifuBinding? = null
    private val binding get() = _binding!!

    private var viewModel: FavoriteWaifuViewModel? = null

    private val adapter = WaifuAdapter()

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




        viewModel = ViewModelProvider(this, FavoriteWaifuViewModel.getFactory())
            .get(FavoriteWaifuViewModel::class.java)

        viewModel?.observeWaifuList()?.observe(viewLifecycleOwner) { waifuList ->
            adapter.waifu = waifuList
        }

        viewModel?.getWaifuList()

        binding.waifuRecycleView.adapter = adapter
        binding.waifuRecycleView.layoutManager = LinearLayoutManager(requireContext())

        binding.clearWaifu.setOnClickListener {

            viewModel?.clearWaifu()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}





