package com.g2pdev.simpletranslator.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteTranslationsDao {

    @Query("SELECT * FROM favorite_translations")
    fun getAll(): Single<List<FavoriteTranslation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favoriteTranslation: FavoriteTranslation): Completable

    @Query("SELECT COUNT(*) FROM favorite_translations WHERE source_language = :sourceLanguageCode AND target_language = :targetLanguageCode AND source_text = :sourceText AND target_text = :targetText")
    fun getCount(sourceLanguageCode: String, targetLanguageCode: String, sourceText: String, targetText: String): Single<Int>

    @Query("DELETE FROM favorite_translations WHERE source_language = :sourceLanguageCode AND target_language = :targetLanguageCode AND source_text = :sourceText AND target_text = :targetText")
    fun delete(sourceLanguageCode: String, targetLanguageCode: String, sourceText: String, targetText: String): Completable

    @Query("DELETE FROM favorite_translations")
    fun deleteAll(): Completable

}