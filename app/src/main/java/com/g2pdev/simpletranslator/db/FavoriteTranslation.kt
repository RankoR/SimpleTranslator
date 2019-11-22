package com.g2pdev.simpletranslator.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_translations")
data class FavoriteTranslation(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "source_language")
    val sourceLanguageCode: String,

    @ColumnInfo(name = "target_language")
    val targetLanguageCode: String,

    @ColumnInfo(name = "source_text")
    val sourceText: String,

    @ColumnInfo(name = "target_text")
    val targetText: String
)