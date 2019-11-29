package com.g2pdev.translation.translation.interactor

import com.g2pdev.translation.translation.exception.ModelNotDownloadedException
import com.g2pdev.translation.translation.interactor.model.IsModelDownloaded
import com.g2pdev.translation.translation.language.LanguagePair
import com.g2pdev.translation.translation.translator.Translator
import io.reactivex.Single
import io.reactivex.functions.BiFunction

interface Translate {
    fun exec(languagePair: LanguagePair, text: String): Single<String>
}

class TranslateImpl(
    private val translator: Translator,
    private val isModelDownloaded: IsModelDownloaded
) : Translate {

    override fun exec(languagePair: LanguagePair, text: String): Single<String> {
        return Single.zip(
            isModelDownloaded.exec(languagePair.source),
            isModelDownloaded.exec(languagePair.target),
            BiFunction<Boolean, Boolean, Boolean> { first, second ->
                first && second
            }
        ).flatMap { isDownloaded ->
            if (isDownloaded) {
                translator.translate(languagePair, text)
            } else {
                Single.error(ModelNotDownloadedException(languagePair))
            }
        }
    }
}