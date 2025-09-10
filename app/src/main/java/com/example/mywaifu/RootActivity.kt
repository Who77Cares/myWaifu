package com.example.mywaifu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.mywaifu.databinding.ActivityRootBinding
import com.example.mywaifu.ui.fragments.OneWaifuFragment
import com.google.android.material.tabs.TabLayoutMediator

class RootActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding
    private var tabMediator: TabLayoutMediator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)



            // Подключаем адаптер с двумя фрагментами
            binding.viewPager.adapter = TabAdapter(
                fragmentManager = supportFragmentManager,
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




}