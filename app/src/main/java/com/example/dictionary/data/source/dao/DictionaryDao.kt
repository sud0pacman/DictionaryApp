package com.example.dictionary.data.source.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.data.source.entity.Dictionary

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary")
    fun allWord(): Cursor

    @Query("SELECT * FROM dictionary WHERE  dictionary.english LIKE :query || '%'")
    fun getWordsByQueryEn(query: String): Cursor

    @Query("SELECT * FROM dictionary WHERE dictionary.uzbek LIKE :query || '%'")
    fun getWordsByQueryUz(query: String): Cursor

    @Query("SELECT * FROM dictionary WHERE dictionary.english = :query LIMIT 1")
    fun getEnglishWord(query: String): Cursor

    @Query("SELECT * FROM dictionary WHERE dictionary.uzbek = :query LIMIT 1")
    fun getUzbekWord(query: String): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateWord(dictionary: Dictionary)

    @Query("SELECT * FROM dictionary WHERE dictionary.is_favourite =:typeBookmark")
    fun getEngBookmark(typeBookmark: Int): Cursor

    @Query("SELECT * FROM dictionary WHERE dictionary.is_favourite =:query")
    fun getBookmarks(query: Int): Cursor
}