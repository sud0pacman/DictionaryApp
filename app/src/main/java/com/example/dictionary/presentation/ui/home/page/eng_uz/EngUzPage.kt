package com.example.dictionary.presentation.ui.home.page.eng_uz

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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentEngUzBinding
import com.example.dictionary.presentation.adapter.EngUzWordAdapter
import com.example.dictionary.utils.capitalizeFirstLetter
import java.util.Locale

class EngUzPage : Fragment(R.layout.fragment_eng_uz), EngUzContract.View {
    private val binding by viewBinding( FragmentEngUzBinding::bind)
    private val presenter: EngUzContract.Presenter by lazy { EngUzPresenter(this) }
    private lateinit var adapter: EngUzWordAdapter
    private var currentQuery: String? = null

    private lateinit var mTTS: TextToSpeech

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        requireActivity().runOnUiThread {
            initView()
        }
    }

    override fun showWords(cursor: Cursor) {
        requireActivity().runOnUiThread {
            adapter.setCursor(cursor, currentQuery)
        }
    }

    private fun initView() {
        adapter = EngUzWordAdapter()
        binding.rvEngUz.adapter = adapter


//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean = true
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                currentQuery = newText
//
//                if (currentQuery == null) presenter.loadWords()
//                else presenter.loadWordsByQuery(currentQuery!!)
//
//                return true
//            }
//        })
//
//
//        val closeBtn = binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
//
//        closeBtn.setOnClickListener {
//            binding.searchView.setQuery(null, false)
//            binding.searchView.clearFocus()
//        }

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

    override fun onDestroy() {
        super.onDestroy()
    }
}