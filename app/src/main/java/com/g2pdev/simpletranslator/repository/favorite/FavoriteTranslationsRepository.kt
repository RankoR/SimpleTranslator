package com.g2pdev.simpletranslator.repository.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.db.FavoriteTranslationsDao
import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteTranslationsRepository {

    fun getTranslations(): Single<List<FavoriteTranslation>>
    fun addTranslation(favoriteTranslation: FavoriteTranslation): Completable
    fun deleteTranslation(favoriteTranslation: FavoriteTranslation): Completable

    fun containsTranslation(sourceText: String, targetText: String): Single<Boolean>
}

class FavoriteTranslationsRepositoryImpl(
    private val favoriteTranslationsDao: FavoriteTranslationsDao
) : FavoriteTranslationsRepository {

    override fun getTranslations(): Single<List<FavoriteTranslation>> {
        return favoriteTranslationsDao.getAll()
    }

    override fun addTranslation(favoriteTranslation: FavoriteTranslation): Completable {
        return favoriteTranslationsDao.add(favoriteTranslation)
    }

    override fun deleteTranslation(favoriteTranslation: FavoriteTranslation): Completable {
        return favoriteTranslationsDao.delete(
            favoriteTranslation.sourceLanguageCode,
            favoriteTranslation.targetLanguageCode,
            favoriteTranslation.sourceText,
            favoriteTranslation.targetText
        )
    }

    override fun containsTranslation(sourceText: String, targetText: String): Single<Boolean> {
        return getTranslations()
            .map { translationFavorites ->
                translationFavorites.find { it.sourceText == sourceText && it.targetText == targetText } != null
            }
    }
}