package com.example.dictionary.presentation.ui.search

import android.database.Cursor

interface SearchContract {

    interface Model {
        fun getWords(): Cursor
        fun getEngWordsByQuery(query: String): Cursor
        fun getUzWordsByQuery(query: String): Cursor
    }

    interface View {
        fun showWords(cursor: Cursor)
        fun setTitle(title: String)
    }

    interface Presenter {
        fun getWords()
        fun loadWordsByQuery(query: String)
        fun changeLanguage()
        fun setLanguage(int: Int)
    }

}