package com.example.dictionary.presentation.ui.search

import android.app.Dialog
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.databinding.ScreenSearchBinding
import com.example.dictionary.presentation.adapter.SearchAdapter
import com.example.dictionary.utils.capitalizeFirstLetter
import java.util.Locale

class SearchFragment : Fragment(R.layout.screen_search), SearchContract.View {
    private var _binding: ScreenSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchAdapter
    private lateinit var presenter: SearchPresenter
    private var currentQuery: String? = null
    private lateinit var mTTS: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ScreenSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().window.statusBarColor = Color.parseColor("#008c4d")
        initView()
    }

    override fun showWords(cursor: Cursor) {
        requireActivity().runOnUiThread {
            adapter.setCursor(cursor, currentQuery)
        }
    }

    override fun setTitle(title: String) {
        requireActivity().runOnUiThread { binding.searchTitle.text = title }
    }


    private fun initView() {
        presenter = SearchPresenter(this)
        adapter = SearchAdapter()

        binding.rvSearch.adapter = adapter

        presenter.getWords()

        binding.searchView.requestFocus()

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}


            override fun afterTextChanged(s: Editable?) {
                currentQuery = s.toString().trim()

                if (currentQuery!!.isNotEmpty()) {
                    presenter.loadWordsByQuery(currentQuery!!)
                    binding.icClose.isVisible = true
                }
                else {
                    presenter.loadWordsByQuery("")
                    binding.icClose.isVisible = false
                }
            }

        })

        binding.icClose.setOnClickListener {
            binding.searchView.setText("")
            presenter.loadWordsByQuery("")
        }


        binding.icBtnTransfer.setOnClickListener {
            presenter.changeLanguage()
            adapter.changeTransfer()
        }

        binding.icBackBtn.setOnClickListener { findNavController().popBackStack() }

        binding.icMicrophone.setOnClickListener { findNavController().navigate(R.id.action_searchFragment2_to_voiceSearchScreen) }

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

        adapter.updateWord = {
            presenter.updateWordMark(it)
        }

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_word_info)

        adapter.clickWordItem = { english, uzbek, transicript ->
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable( Color.TRANSPARENT))
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setGravity(Gravity.CENTER)

            dialog.findViewById<TextView>(R.id.main_word).text = capitalizeFirstLetter(english)
            dialog.findViewById<TextView>(R.id.sub_word).text = uzbek
            dialog.findViewById<TextView>(R.id.tv_transcription).text = "noun:[${transicript}]"

            dialog.findViewById<TextView>(R.id.btn_close).setOnClickListener { dialog.dismiss() }
        }
    }


    private fun speak(text: String) {
        mTTS.setSpeechRate(0.45f)
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }
}