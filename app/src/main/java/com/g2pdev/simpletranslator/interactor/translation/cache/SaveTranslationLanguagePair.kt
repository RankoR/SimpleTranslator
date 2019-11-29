package com.g2pdev.simpletranslator.interactor.translation.cache

import com.g2pdev.simpletranslator.cache.TranslationModelsCache
import com.g2pdev.translation.translation.language.LanguagePair
import io.reactivex.Completable

interface SaveTranslationLanguagePair {
    fun exec(languagePair: LanguagePair): Completable
}

class SaveTranslationLanguagePairImpl(
    private val translationModelsCache: TranslationModelsCache
) : SaveTranslationLanguagePair {

    override fun exec(languagePair: LanguagePair) = translationModelsCache.save(languagePair)
}