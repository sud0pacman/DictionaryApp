package com.example.dictionary.presentation.ui.home.page.eng_uz

import android.database.Cursor
import com.example.dictionary.data.source.entity.Dictionary
import com.example.dictionary.domain.AppRepositoryImpl

class EngUzModel : EngUzContract.Model {
    private val repository = AppRepositoryImpl.getInstance()

    override fun loadWords(): Cursor = repository.allWords()
    override fun loadWordsByQueryEn(query: String): Cursor = repository.getAllWordsByQueryEn(query)
    override fun loadWordsByQueryUz(query: String): Cursor = repository.getAllWordsByQueryUz(query)
    override fun updateWordMark(dictionary: Dictionary) = repository.updateDictionary(dictionary)
}