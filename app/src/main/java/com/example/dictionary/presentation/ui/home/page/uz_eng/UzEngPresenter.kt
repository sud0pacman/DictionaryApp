package com.example.dictionary.presentation.ui.home.page.uz_eng

import com.example.dictionary.data.source.entity.Dictionary
import java.util.concurrent.Executors

class UzEngPresenter(private val view: UzEngContract.View) : UzEngContract.Presenter {
    private val model = UzEngModel()
    private val executors = Executors.newSingleThreadExecutor()


    override fun loadWords() {
        executors.execute {
            val cursor = model.loadWords()
            view.showWords(cursor)
        }
    }

    override fun loadWordsByQuery(query: String) {
        executors.execute {
            val cursor = model.loadWordsByQueryUz(query)
            view.showWords(cursor)
        }
    }

    override fun updateWordMark(dictionary: Dictionary) {
        executors.execute {
            model.updateWordMark(dictionary)
        }
    }
}