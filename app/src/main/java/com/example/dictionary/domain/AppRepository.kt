package com.example.dictionary.domain

import android.database.Cursor
import com.example.dictionary.data.source.entity.Dictionary

interface AppRepository {

    fun allWords(): Cursor
    fun getAllWordsByQueryEn(query: String) : Cursor
    fun getAllWordsByQueryUz(query: String): Cursor

    fun getEnglishWord(query: String): Cursor
    fun getUzEnglishWord(query: String): Cursor

    fun updateDictionary(dictionary: Dictionary)

    fun getAllBookmarks(query: Int): Cursor
}