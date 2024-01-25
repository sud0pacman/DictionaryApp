package com.example.dictionary.presentation.ui.home.page.eng_uz

import com.example.dictionary.data.source.entity.Dictionary
import java.util.concurrent.Executors

class EngUzPresenter(private val view: EngUzContract.View) : EngUzContract.Presenter {
    private val model = EngUzModel()
    private val executors = Executors.newSingleThreadExecutor()
    private var lang = true


    override fun loadWords() {
        executors.execute {
            val cursor = model.loadWords()
            view.showWords(cursor)
        }
    }

    override fun loadWordsByQuery(query: String) {
        executors.execute {
            if (lang) {
                val cursor = model.loadWordsByQueryEn(query)
                view.showWords(cursor)
            }
            else {
                val cursor = model.loadWordsByQueryUz(query)
                view.showWords(cursor)
            }
        }
    }

    override fun transferLang() {
        lang = !lang
        loadWords()
    }

    override fun updateWordMark(dictionary: Dictionary) {
        executors.execute {
            model.updateWordMark(dictionary)
        }
    }
}