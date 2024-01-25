package com.example.dictionary.presentation.ui.voice_search

import android.annotation.SuppressLint
import java.util.concurrent.Executors

class VoiceSearchPresenter(private val view: VoiceSearchContract.View) : VoiceSearchContract.Presenter {
    private val model = VoiceSearchModel()
    private var lang: Boolean = true

    private val executors = Executors.newSingleThreadExecutor()

    @SuppressLint("Range")
    override fun loadWords(query: String) {
        executors.execute {
            if (lang) {
                val cursor = model.getEngWordByQuery(query)

                cursor.let {
                    cursor.moveToFirst()
                    while (!it.isAfterLast) {
                        val english = it.getString(it.getColumnIndex("english"))
                        val uzbek = it.getString(it.getColumnIndex("uzbek"))

                        it.moveToNext()

                        if (english == query) {
                            it.moveToLast()
                            it.moveToNext()
                        }

                        view.showWords(english, uzbek)
                    }
                }
            }
            else {
                val cursor = model.getUzWordByQuery(query)

                cursor.let {
                    cursor.moveToFirst()

                    while (!it.isAfterLast) {
                        val english = it.getString(it.getColumnIndex("english"))
                        val uzbek = it.getString(it.getColumnIndex("uzbek"))


                        it.moveToNext()

                        if (uzbek == query) {
                            it.moveToLast()
                            it.moveToNext()
                        }

                        view.showWords(uzbek, english)
                    }

                }
            }
        }
    }

    override fun clickTransfer() {
        lang = !lang

        if (lang) {
            view.changeLang("eng-Eng")
            view.setLang("English", "Uzbek")
            view.setConditions("Search only one word by voice")
        } else {
            view.changeLang("uz-Uz")
            view.setLang("Uzbek", "English")
            view.setConditions("Ovoz orqali so'z qidirish")
        }

    }

    override fun sendQuery(query: String) {
        executors.execute {
        }
    }
}