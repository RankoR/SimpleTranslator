package com.g2pdev.simpletranslator.cache

import android.content.Context
import com.g2pdev.translation.translation.language.LanguagePair
import com.g2pdev.translator.base.cache.BaseCache
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single

interface TranslationModelsCache {
    fun get(): Single<LanguagePair>
    fun save(languagePair: LanguagePair): Completable
}

class TranslationModelsCacheImpl(
    gson: Gson,
    context: Context
) : BaseCache<LanguagePair>(gson,
    key, context,
    name
), TranslationModelsCache {

    override fun getType() = LanguagePair::class.java

    override fun get() = getFromCache()

    override fun save(languagePair: LanguagePair) = putToCache(languagePair)

    private companion object {
        private const val key = "translation_models"
        private const val name = "translation_models"
    }
}