package com.example.dictionary.domain

import android.database.Cursor
import android.util.Log
import com.example.dictionary.data.source.MyDatabase
import com.example.dictionary.data.source.entity.Dictionary
import com.example.dictionary.utils.myLog

class AppRepositoryImpl : AppRepository {

    companion object {
        private lateinit var instance: AppRepository

        fun init() {
            if (!(Companion::instance.isInitialized)) instance = AppRepositoryImpl()
        }

        fun getInstance() = instance
    }



    private val wordDao = MyDatabase.getInstance().getDao()

    override fun allWords(): Cursor = wordDao.allWord()

    override fun getAllWordsByQueryEn(query: String): Cursor = wordDao.getWordsByQueryEn(query)
    override fun getAllWordsByQueryUz(query: String): Cursor = wordDao.getWordsByQueryUz(query)

    override fun getEnglishWord(query: String): Cursor = wordDao.getEnglishWord(query)
    override fun getUzEnglishWord(query: String): Cursor = wordDao.getUzbekWord(query)


    override fun updateDictionary(dictionary: Dictionary) {
        "${dictionary.is_favourite} repo".myLog()
        wordDao.updateWord(dictionary)
    }

    override fun getAllBookmarks(query: Int): Cursor = wordDao.getBookmarks(query)

}