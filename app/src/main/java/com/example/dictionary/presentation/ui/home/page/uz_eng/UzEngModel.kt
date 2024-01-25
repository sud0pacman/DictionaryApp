package com.example.dictionary.presentation.ui.home.page.uz_eng

import android.database.Cursor
import com.example.dictionary.data.source.entity.Dictionary
import com.example.dictionary.domain.AppRepositoryImpl

class UzEngModel : UzEngContract.Model {
    private val repository = AppRepositoryImpl.getInstance()

    override fun loadWords(): Cursor = repository.allWords()
    override fun loadWordsByQueryUz(query: String): Cursor = repository.getAllWordsByQueryUz(query)
    override fun updateWordMark(dictionary: Dictionary)  = repository.updateDictionary(dictionary)
}