package com.example.dictionary.presentation.ui.home.page.uz_eng

import android.app.Dialog
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentUzEngBinding
import com.example.dictionary.presentation.adapter.UzEngWordAdapter
import com.example.dictionary.utils.capitalizeFirstLetter
import java.util.Locale

class UzEngPage : Fragment(R.layout.fragment_uz_eng), UzEngContract.View {
    private val binding: FragmentUzEngBinding by viewBinding(FragmentUzEngBinding::bind)
    private val presenter: UzEngContract.Presenter by lazy { UzEngPresenter(this) }
    private lateinit var adapter: UzEngWordAdapter
    private var currentQuery: String? = null

    private lateinit var mTTS: TextToSpeech


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    override fun showWords(cursor: Cursor) {
        requireActivity().runOnUiThread {
            adapter.setCursor(cursor, currentQuery)
        }
    }


    private fun initView() {
        adapter = UzEngWordAdapter()
        binding.rvUzEng.adapter = adapter

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
            dialog.window?.setBackgroundDrawable(ColorDrawable( Color.TRANSPARENT))
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

    override fun onResume() {
        super.onResume()

        presenter.loadWords()
    }

    private fun speak(text: String) {
        mTTS.setSpeechRate(0.45f)
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }
}