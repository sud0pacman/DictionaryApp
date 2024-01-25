package com.example.dictionary.data.source.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class Dictionary(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var english: String,
    var type: String,
    var transcript: String,
    var uzbek: String,
    var countable: String,
    var is_favourite: Int = 0
)