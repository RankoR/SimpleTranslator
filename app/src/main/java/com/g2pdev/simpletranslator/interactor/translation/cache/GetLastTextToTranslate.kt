package com.g2pdev.simpletranslator.interactor.translation.cache

import com.g2pdev.simpletranslator.cache.TextToTranslateCache
import io.reactivex.Single

interface GetLastTextToTranslate {
    fun exec(): Single<String>
}

class GetLastTextToTranslateImpl(
    private val textToTranslateCache: TextToTranslateCache
) : GetLastTextToTranslate {

    override fun exec(): Single<String> {
        return textToTranslateCache
            .get()
            .onErrorResumeNext { Single.just("") }
    }
}