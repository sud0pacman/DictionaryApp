package com.example.dictionary.presentation.ui.voice_search

import android.database.Cursor

interface VoiceSearchContract {

    interface Model {
        fun getUzWordByQuery(query: String): Cursor
        fun getEngWordByQuery(query: String): Cursor
    }

    interface Presenter {
        fun loadWords(query: String)
        fun clickTransfer()
        fun sendQuery(query: String)
    }


    interface View {
        fun showWords(word1: String, word2: String)
        fun changeLang(lang: String)
        fun setLang(lang1: String, lang2: String)
        fun setConditions(conditions: String)
    }

}