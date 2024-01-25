package com.example.dictionary.presentation.ui.home.page.uz_eng

import android.database.Cursor
import com.example.dictionary.data.source.entity.Dictionary

interface UzEngContract {

    interface Model {
        fun loadWords(): Cursor
        fun loadWordsByQueryUz(query: String): Cursor
        fun updateWordMark(dictionary: Dictionary)
    }


    interface View {
        fun showWords(cursor: Cursor)
    }


    interface Presenter {
        fun loadWords()
        fun loadWordsByQuery(query: String)
        fun updateWordMark(dictionary: Dictionary)
    }

}