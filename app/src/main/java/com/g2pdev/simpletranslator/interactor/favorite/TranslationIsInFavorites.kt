package com.g2pdev.simpletranslator.interactor.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.repository.favorite.FavoriteTranslationsRepository
import io.reactivex.Single

interface TranslationIsInFavorites {
    fun exec(favoriteTranslation: FavoriteTranslation): Single<Boolean>
}

class TranslationIsInFavoritesImpl(
    private val translationsRepository: FavoriteTranslationsRepository
) : TranslationIsInFavorites {

    override fun exec(favoriteTranslation: FavoriteTranslation): Single<Boolean> {
        return translationsRepository.containsTranslation(favoriteTranslation)
    }
}
