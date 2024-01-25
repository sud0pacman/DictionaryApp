package com.example.dictionary.presentation.ui.voice_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.fragment.app.Fragment
import com.example.dictionary.MainActivity
import com.example.dictionary.databinding.ScreenVoiceSearchBinding
import com.example.dictionary.utils.capitalizeFirstLetter

class VoiceSearchScreen : Fragment(), VoiceSearchContract.View {

    private var _binding: ScreenVoiceSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var lang: String

    private lateinit var presenter: VoiceSearchPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenVoiceSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = VoiceSearchPresenter(this)
        lang = "eng-Eng"
        setLang("English", "Uzbek")

        binding.btnMicrophone.setOnClickListener {
            (requireActivity() as MainActivity).promptSpeechInput(lang)
        }


        (requireActivity() as MainActivity).shareData = {

            if (it.contains(' ')) {
                presenter.loadWords(it.substring(1, it.indexOf(' ')).lowercase())
            }
            else {
                presenter.loadWords(it.substring(1, it.indexOf(']')).lowercase())
            }
        }


        val scaleAnimation = ScaleAnimation(
            0.2f,   // From X
            1.0f,   // To X
            0.2f,   // From Y
            1.0f,   // To Y
            Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot X (center of the button)
            Animation.RELATIVE_TO_SELF, 0.5f   // Pivot Y (center of the button)
        )


        scaleAnimation.duration = 500  // Animation duration in milliseconds
//        scaleAnimation.repeatMode = ScaleAnimation.REVERSE  // Reverse the animation
//        scaleAnimation.repeatCount = Animation.INFINITE

        binding.btnTransferVoiceLang.setOnClickListener {
            binding.leftLang.startAnimation(scaleAnimation)
            binding.rightLang.startAnimation(scaleAnimation)

            presenter.clickTransfer()
        }
    }

    override fun showWords(word1: String, word2: String) {
        requireActivity().runOnUiThread {
            binding.containerLang1.text = capitalizeFirstLetter(word1)
            binding.containerLang2.text = capitalizeFirstLetter(word2)
        }
    }

    override fun changeLang(lang: String) {
        this.lang = lang
    }


    override fun setLang(lang1: String, lang2: String) {
        binding.tvLang1.text = lang1
        binding.tvLang2.text = lang2

        val temp = binding.containerLang1.text
        binding.containerLang1.text = binding.containerLang2.text
        binding.containerLang2.text = temp
    }
}









