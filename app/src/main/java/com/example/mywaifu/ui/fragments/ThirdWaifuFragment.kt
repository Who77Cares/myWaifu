package com.example.mywaifu.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mywaifu.databinding.FragmentThirdWaifuBinding

class ThirdWaifuFragment: Fragment() {

    private var _binding: FragmentThirdWaifuBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdWaifuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = "Это третий экран внутри первого таба 👋"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}