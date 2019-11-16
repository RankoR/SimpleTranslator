package com.g2pdev.simpletranslator.interactor.translation

import com.g2pdev.simpletranslator.interactor.translation.models.IsModelDownloaded
import com.g2pdev.simpletranslator.translation.exception.ModelNotDownloadedException
import com.g2pdev.simpletranslator.translation.language.LanguagePair
import com.g2pdev.simpletranslator.translation.translator.Translator
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