package com.example.dictionary.presentation.ui.search

import android.database.Cursor
import com.example.dictionary.data.source.entity.Dictionary
import com.example.dictionary.domain.AppRepositoryImpl

class SearchModel : SearchContract.Model {
    private val repository = AppRepositoryImpl.getInstance()

    override fun getWords(): Cursor = repository.allWords()

    override fun getEngWordsByQuery(query: String): Cursor = repository.getAllWordsByQueryEn(query)

    override fun getUzWordsByQuery(query: String): Cursor = repository.getAllWordsByQueryUz(query)

    override fun updateWordMark(dictionary: Dictionary) = repository.updateDictionary(dictionary)
}