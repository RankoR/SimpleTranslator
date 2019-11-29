package com.g2pdev.simpletranslator.interactor.translation.cache

import com.g2pdev.translation.translation.util.LanguageNameProvider
import com.g2pdev.simpletranslator.cache.TranslationModelsCache
import com.g2pdev.translation.translation.language.Language
import com.g2pdev.translation.translation.language.LanguagePair
import com.g2pdev.translation.translation.model.TranslationModel
import io.reactivex.Single
import timber.log.Timber
import java.util.*

interface GetTranslationLanguagePair {
    fun exec(): Single<LanguagePair>
}

class GetTranslationLanguagePairImpl(
    private val translationModelsCache: TranslationModelsCache,
    private val languageNameProvider: LanguageNameProvider
) : GetTranslationLanguagePair {

    private fun getDefaultLanguagePair(): LanguagePair {
        val currentLanguageCode = Locale.getDefault().language

        val sourceLanguage = defaultSourceLanguage
        val targetLanguage = try {
            Language.valueOf(currentLanguageCode)
        } catch (e: IllegalArgumentException) {
            Timber.w("No language for code $currentLanguageCode")
            defaultTargetLanguage
        }

        val sourceTranslationModel = TranslationModel(sourceLanguage, languageNameProvider.getNameForLanguage(sourceLanguage))
        val targetTranslationModel = TranslationModel(targetLanguage, languageNameProvider.getNameForLanguage(targetLanguage))

        return LanguagePair(sourceTranslationModel, targetTranslationModel)
    }

    override fun exec(): Single<LanguagePair> {
        return translationModelsCache
            .get()
            .onErrorResumeNext { Single.just(getDefaultLanguagePair()) }
    }

    private companion object {
        private val defaultSourceLanguage = Language.EN
        private val defaultTargetLanguage = Language.RU
    }
}