package com.example.dictionary.app

import android.app.Application
import com.example.dictionary.data.source.MyDatabase
import com.example.dictionary.domain.AppRepositoryImpl

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        MyDatabase.init(this)
        AppRepositoryImpl.init()
    }
}