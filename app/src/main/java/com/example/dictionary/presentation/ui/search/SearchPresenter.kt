package com.example.dictionary.presentation.ui.search

import android.database.Cursor
import android.util.Log
import java.util.concurrent.Executors

class SearchPresenter(private val view: SearchContract.View) : SearchContract.Presenter {
    private val executors = Executors.newSingleThreadExecutor()
    private val model = SearchModel()
    private var curLang = true

    override fun getWords() {
        executors.execute {
            if (curLang) {
                val cursor = model.getEngWordsByQuery("")
                view.showWords(cursor)
            }
            else {
                val cursor = model.getUzWordsByQuery("")
                view.showWords(cursor)
            }
        }
    }

    override fun setLanguage(int: Int) {
        if (int == 0) {
            curLang = true
            view.setTitle("Eng-Uz")
        }
        else {
            curLang = false
            view.setTitle("Uz-Eng")
        }
    }

    override fun loadWordsByQuery(query: String) {
        executors.execute {

            if (curLang) {
                val cursor = model.getEngWordsByQuery(query)
                view.showWords(cursor)
            }
            else {
                val cursor = model.getUzWordsByQuery(query)
                view.showWords(cursor)
            }

        }
    }


    override fun changeLanguage() {
        curLang = !curLang

       view.setTitle(if (curLang) "Eng-Uz" else "Uz-Eng")
    }
}