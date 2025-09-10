package com.example.mywaifu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.mywaifu.databinding.ActivityRootBinding
import com.example.mywaifu.ui.fragments.OneWaifuFragment
import com.example.mywaifu.ui.fragments.TabFragment
import com.google.android.material.tabs.TabLayoutMediator

class RootActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .add(
                    R.id.rootFragmentContainerView,
                    TabFragment()
                )
                .commit()

        }

    }
}