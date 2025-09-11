package com.example.mywaifu.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback

import androidx.fragment.app.Fragment
import com.example.mywaifu.databinding.FragmentFirstTabHostBinding

class FirstTabHostFragment: Fragment() {

    private var _binding: FragmentFirstTabHostBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstTabHostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // стартовый экран внутри 1-го таба — твой OneWaifuFragment
        if (childFragmentManager.findFragmentById(binding.tabContainer.id) == null) {
            childFragmentManager.beginTransaction()
                .replace(binding.tabContainer.id, OneWaifuFragment())
                .commit()
        }

        // обработка системной "Назад": если открыт третий — popBackStack, иначе отдадим событие выше
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (childFragmentManager.backStackEntryCount > 0) {
                        childFragmentManager.popBackStack()
                    } else {
                        // нет внутреннего стека — отдаём назад наверх (TabFragment обработает переход на 1-й таб)
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }

            }
        )
    }

    fun openThirdInsideFirstTab() {
        childFragmentManager.beginTransaction()
            .replace(binding.tabContainer.id, ThirdWaifuFragment())
            .addToBackStack("third_inside_first_tab")
            .commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}