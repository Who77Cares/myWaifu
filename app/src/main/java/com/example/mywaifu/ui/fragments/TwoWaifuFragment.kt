package com.example.mywaifu.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.example.mywaifu.databinding.FragmentTwoWaifuBinding
import com.example.mywaifu.ui.WaifuAdapter
import com.example.mywaifu.ui.fragments.view_models.TwoWaifuViewModel
import com.example.mywaifu.GlobalState


class TwoWaifuFragment: Fragment() {

    private var viewModel: TwoWaifuViewModel? = null

    private var _binding: FragmentTwoWaifuBinding? = null
    private val binding get() = _binding!!

    private val adapter: WaifuAdapter = WaifuAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTwoWaifuBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel?.getManyWaifu("nsfw", "waifu", false)



        viewModel = ViewModelProvider(this, TwoWaifuViewModel.getFactory())
            .get(TwoWaifuViewModel::class.java)

        viewModel?.observeState()?.observe(viewLifecycleOwner) {
            render(it)
        }

        binding.manyWaifuRecycleView.adapter = adapter
        binding.manyWaifuRecycleView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.reload.setOnClickListener {
            viewModel?.getManyWaifu("nsfw", "waifu", false)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun render(state: GlobalState) {
        when (state) {
            is GlobalState.Error -> showError(state.message)
            is GlobalState.ManyContent -> showManyContent(state.manyUrl)
            GlobalState.Loading -> showLading()
            is GlobalState.Content -> {
                Log.d("render fun в TwoWaifuFragment", "Сюда мы попасть не должны")
            }

        }
    }

    private fun showError(message: String) {
        binding.progressBar2.visibility = View.VISIBLE
        binding.manyWaifuRecycleView.visibility = View.INVISIBLE
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showLading() {

        binding.apply {
            manyWaifuRecycleView.visibility = View.GONE
            progressBar2.visibility = View.VISIBLE
            progressBar2.repeatMode = LottieDrawable.RESTART
            progressBar2.repeatCount = LottieDrawable.INFINITE
            progressBar2.playAnimation()
        }
    }

    private fun showManyContent(manyUrl: List<String>) {
        binding.progressBar2.visibility = View.GONE
        binding.manyWaifuRecycleView.visibility = View.VISIBLE

        adapter.waifu = manyUrl
        adapter.notifyDataSetChanged()

//        Toast.makeText(requireContext(), "Много контента пришело", Toast.LENGTH_LONG).show()
    }
}