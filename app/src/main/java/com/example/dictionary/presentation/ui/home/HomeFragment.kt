package com.example.dictionary.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentHomeBinding
import com.example.dictionary.databinding.FragmentHomeNavBinding
import com.example.dictionary.presentation.adapter.HomePageAdapter
import com.example.dictionary.utils.myLog
import com.example.dictionary.utils.replaceScreen
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeNavBinding? = null
    private val binding get() = _binding!!

    private lateinit var pageAdapter: FragmentStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeNavBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pageAdapter = HomePageAdapter(childFragmentManager, lifecycle)

        binding.appBarMain.viewPager.adapter = pageAdapter

        binding.appBarMain.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.appBarMain.viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


        binding.appBarMain.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.appBarMain.tabLayout.getTabAt(position)?.select()
            }

//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                "position $position".myLog()
//                if (position == 1) {
//                    binding.appBarMain.tabLayout.translationX = -positionOffsetPixels.toFloat()
//                }
//                else binding.appBarMain.tabLayout.translationX = positionOffsetPixels.toFloat()
//            }
        })


        binding.appBarMain.openMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.appBarMain.viewPager.isUserInputEnabled = false

        binding.appBarMain.btnSearch.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_searchFragment2,
                Bundle().apply {
                    putInt("currentLang", binding.appBarMain.viewPager.currentItem)
                })
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_bookmarks -> {
                    findNavController().navigate(R.id.action_homeFragment_to_bookFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }

            return@setNavigationItemSelectedListener true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}