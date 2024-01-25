package com.example.dictionary.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dictionary.presentation.ui.home.page.eng_uz.EngUzPage
import com.example.dictionary.presentation.ui.home.page.uz_eng.UzEngPage

class HomePageAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> EngUzPage()
            1 -> UzEngPage()
            else -> EngUzPage()
        }
    }
}