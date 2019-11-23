package com.g2pdev.simpletranslator.repository.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.db.FavoriteTranslationsDao
import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteTranslationsRepository {

    fun getTranslations(): Single<List<FavoriteTranslation>>
    fun addTranslation(favoriteTranslation: FavoriteTranslation): Completable
    fun deleteTranslation(favoriteTranslation: FavoriteTranslation): Completable

    fun containsTranslation(favoriteTranslation: FavoriteTranslation): Single<Boolean>
}

class FavoriteTranslationsRepositoryImpl(
    private val favoriteTranslationsDao: FavoriteTranslationsDao
) : FavoriteTranslationsRepository {

    override fun getTranslations(): Single<List<FavoriteTranslation>> {
        return favoriteTranslationsDao
            .getAll()
            .map { translations ->
                translations.sortedByDescending { it.id }
            }
    }

    override fun addTranslation(favoriteTranslation: FavoriteTranslation): Completable {
        return favoriteTranslationsDao.add(favoriteTranslation)
    }

    override fun deleteTranslation(favoriteTranslation: FavoriteTranslation): Completable {
        return favoriteTranslationsDao
            .delete(
                favoriteTranslation.sourceLanguageCode,
                favoriteTranslation.targetLanguageCode,
                favoriteTranslation.sourceText,
                favoriteTranslation.targetText
            )
    }

    override fun containsTranslation(favoriteTranslation: FavoriteTranslation): Single<Boolean> {
        return favoriteTranslationsDao
            .getCount(
                favoriteTranslation.sourceLanguageCode,
                favoriteTranslation.targetLanguageCode,
                favoriteTranslation.sourceText,
                favoriteTranslation.targetText
            )
            .map { it > 0 }
    }
}