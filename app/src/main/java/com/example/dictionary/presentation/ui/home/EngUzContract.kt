package com.example.dictionary.presentation.ui.home

import android.database.Cursor
import com.example.dictionary.data.source.entity.Dictionary

interface EngUzContract {

    interface Model {
        fun loadWords(): Cursor
        fun loadWordsByQueryEn(query: String): Cursor
        fun loadWordsByQueryUz(query: String): Cursor
        fun updateWordMark(dictionary: Dictionary)
    }


    interface View {
        fun showWords(cursor: Cursor)
    }


    interface Presenter {
        fun loadWords()
        fun loadWordsByQuery(query: String)
        fun transferLang()
        fun updateWordMark(dictionary: Dictionary)
    }

}