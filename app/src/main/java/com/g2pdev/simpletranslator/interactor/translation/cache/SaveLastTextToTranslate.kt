package com.g2pdev.simpletranslator.interactor.translation.cache

import com.g2pdev.simpletranslator.cache.TextToTranslateCache
import io.reactivex.Completable

interface SaveLastTextToTranslate {
    fun exec(text: String): Completable
}

class SaveLastTextToTranslateImpl(
    private val textToTranslateCache: TextToTranslateCache
) : SaveLastTextToTranslate {

    override fun exec(text: String) = textToTranslateCache.save(text)
}