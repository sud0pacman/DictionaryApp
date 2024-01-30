package com.example.dictionary.presentation.ui.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.databinding.ScreenSplashBinding
import com.example.dictionary.presentation.ui.home.HomeFragment

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {
    private var _binding : ScreenSplashBinding? = null
    private val binding get() =  _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenSplashBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()

        Handler().postDelayed({
            navController.navigate(R.id.action_splashScreen_to_homeFragment)
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}