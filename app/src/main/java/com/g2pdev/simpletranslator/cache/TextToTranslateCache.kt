package com.g2pdev.simpletranslator.cache

import android.content.Context
import com.g2pdev.translator.base.cache.BaseCache
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single

interface TextToTranslateCache {
    fun get(): Single<String>
    fun save(text: String): Completable
}

class TextToTranslateCacheImpl(
    gson: Gson,
    context: Context
) : BaseCache<String>(gson, key, context, name), TextToTranslateCache {

    override fun get(): Single<String> {
        return getFromCache()
    }

    override fun save(text: String): Completable {
        return putToCache(text)
    }

    override fun getType() = String::class.java

    private companion object {
        private const val name = "text_to_translate"
        private const val key = "text_to_translate"
    }

}