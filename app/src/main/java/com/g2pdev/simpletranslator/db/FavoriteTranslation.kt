package com.g2pdev.simpletranslator.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_translations",
    indices = [
        Index(
            value = ["source_language", "target_language", "source_text", "target_text"],
            unique = true
        )
    ]
)
data class FavoriteTranslation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "source_language")
    val sourceLanguageCode: String,

    @ColumnInfo(name = "target_language")
    val targetLanguageCode: String,

    @ColumnInfo(name = "source_text")
    val sourceText: String,

    @ColumnInfo(name = "target_text")
    val targetText: String
) {

    override fun equals(other: Any?): Boolean {
        if (other !is FavoriteTranslation) {
            return false
        }

        if (sourceLanguageCode != other.sourceLanguageCode) {
            return false
        }

        if (targetLanguageCode != other.targetLanguageCode) {
            return false
        }

        if (sourceText != other.sourceText) {
            return false
        }

        if (targetText != other.targetText) {
            return false
        }

        return true
    }


}