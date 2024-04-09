package com.example.dictionary.presentation.ui.home

import android.app.Dialog
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentHomeNavBinding
import com.example.dictionary.presentation.adapter.EngUzWordAdapter
import com.example.dictionary.utils.capitalizeFirstLetter
import java.util.Locale

class HomeFragment : Fragment(), EngUzContract.View {

    private var _binding: FragmentHomeNavBinding? = null
    private val binding get() = _binding!!

    private val presenter: EngUzContract.Presenter by lazy { EngUzPresenter(this) }
    private lateinit var adapter: EngUzWordAdapter
    private var currentQuery: String? = null

    private lateinit var mTTS: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        requireActivity().window.statusBarColor = Color.parseColor("#008c4d")


        _binding = FragmentHomeNavBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        initView()

        binding.appBarMain.openMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }


        binding.appBarMain.btnSearch.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_searchFragment2
            )
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_bookmarks -> {
                    findNavController().navigate(R.id.action_homeFragment_to_bookFragment)
                }

                R.id.nav_voice -> {
                    findNavController().navigate(R.id.action_homeFragment_to_voiceSearchScreen)
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)

            return@setNavigationItemSelectedListener true
        }


        binding.appBarMain.icTransfer.setOnClickListener {
            presenter.transferLang()
            adapter.changeTransfer()
        }
    }

    override fun showWords(cursor: Cursor) {
        requireActivity().runOnUiThread {
            adapter.setCursor(cursor, currentQuery)
        }
    }

    private fun initView() {
        adapter = EngUzWordAdapter()
        binding.appBarMain.rv.adapter = adapter
        binding.appBarMain.rv.layoutManager = LinearLayoutManager(requireContext())

        presenter.loadWords()



        mTTS = TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS.setLanguage(Locale.ENGLISH)
                if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.e("TTS", "Language not supported")
                } else {
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }

        adapter.clickVolume = {
            speak(it)
        }

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_word_info)

        adapter.clickWordItem = { english, uzbek, transicript ->
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setGravity(Gravity.CENTER)

            dialog.findViewById<TextView>(R.id.main_word).text = capitalizeFirstLetter(english)
            dialog.findViewById<TextView>(R.id.sub_word).text = uzbek
            dialog.findViewById<TextView>(R.id.tv_transcription).text = "noun:[${transicript}]"

            dialog.findViewById<TextView>(R.id.btn_close).setOnClickListener { dialog.dismiss() }
        }

        adapter.updateWord = {
            presenter.updateWordMark(it)
        }
    }

    private fun speak(text: String) {
        mTTS.setSpeechRate(0.45f)
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}