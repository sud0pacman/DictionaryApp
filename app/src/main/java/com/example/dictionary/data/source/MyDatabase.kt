package com.example.dictionary.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionary.data.source.dao.DictionaryDao
import com.example.dictionary.data.source.entity.Dictionary

@Database(entities = [Dictionary::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getDao(): DictionaryDao

    companion object {
        @Volatile
        private lateinit var instance: MyDatabase

        fun init(context: Context) {
            if (!(Companion::instance.isInitialized)) {
                instance = Room.databaseBuilder(context, MyDatabase::class.java, "MyDictionary.db")
                    .createFromAsset("dictionary.db")
                    .build()
            }
        }

        fun getInstance(): MyDatabase = instance
    }

}