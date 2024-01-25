package com.example.dictionary.presentation.ui.search

import android.database.Cursor
import com.example.dictionary.data.source.entity.Dictionary

interface SearchContract {

    interface Model {
        fun getWords(): Cursor
        fun getEngWordsByQuery(query: String): Cursor
        fun getUzWordsByQuery(query: String): Cursor
        fun updateWordMark(dictionary: Dictionary)
    }

    interface View {
        fun showWords(cursor: Cursor)
        fun setTitle(title: String)
    }

    interface Presenter {
        fun getWords()
        fun loadWordsByQuery(query: String)
        fun changeLanguage()
        fun updateWordMark(dictionary: Dictionary)
        fun setLanguage(int: Int)
    }

}