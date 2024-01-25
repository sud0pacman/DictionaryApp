package com.example.dictionary.presentation.ui.voice_search

import android.database.Cursor
import com.example.dictionary.domain.AppRepositoryImpl

class VoiceSearchModel : VoiceSearchContract.Model {
    private val repository = AppRepositoryImpl.getInstance()
    override fun getEngWordByQuery(query: String): Cursor = repository.getEnglishWord(query)

    override fun getUzWordByQuery(query: String): Cursor = repository.getUzEnglishWord(query)
}