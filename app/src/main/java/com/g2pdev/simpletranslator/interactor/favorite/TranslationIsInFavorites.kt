package com.g2pdev.simpletranslator.interactor.favorite

import com.g2pdev.simpletranslator.repository.favorite.FavoriteTranslationsRepository
import io.reactivex.Single

interface TranslationIsInFavorites {
    fun exec(sourceText: String, targetText: String): Single<Boolean>
}

class TranslationIsInFavoritesImpl(
    private val translationsRepository: FavoriteTranslationsRepository
) : TranslationIsInFavorites {

    override fun exec(sourceText: String, targetText: String): Single<Boolean> {
        return translationsRepository.containsTranslation(sourceText, targetText)
    }
}
