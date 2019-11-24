package com.g2pdev.simpletranslator.interactor.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.interactor.translation.cache.GetTranslationLanguagePair
import io.reactivex.Single

/**
 * Create (not add!) favorite translation with current language pair and given texts
 */
interface CreateFavoriteTranslation {
    fun exec(sourceText: String, targetText: String): Single<FavoriteTranslation>
}

class CreateFavoriteTranslationImpl(
    private val getTranslationLanguagePair: GetTranslationLanguagePair
) : CreateFavoriteTranslation {

    override fun exec(sourceText: String, targetText: String): Single<FavoriteTranslation> {
        return getTranslationLanguagePair
            .exec()
            .map { languagePair ->
                FavoriteTranslation(
                    sourceLanguageCode = languagePair.source.language.name,
                    targetLanguageCode = languagePair.target.language.name,
                    sourceText = sourceText,
                    targetText = targetText
                )
            }
    }
}