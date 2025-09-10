package com.example.mywaifu.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mywaifu.R
import com.example.mywaifu.TabAdapter
import com.example.mywaifu.databinding.FragmentTabBinding
import com.google.android.material.tabs.TabLayoutMediator

class TabFragment: Fragment() {
    private var _bining: FragmentTabBinding? = null
    private val binding get() = _bining!!

    private var tabMediator: TabLayoutMediator? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bining = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Подключаем адаптер с двумя фрагментами
        binding.viewPager.adapter = TabAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle
        )

// Включить/выключить свайп по желанию
        binding.viewPager.isUserInputEnabled = true
        binding.viewPager.offscreenPageLimit = 1

// Привязка табов
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = when (pos) {
                0 -> "Waifu"
                else -> "Many"
            }
        }
        tabMediator?.attach()
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _bining = null
    }



}


