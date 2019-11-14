package com.g2pdev.simpletranslator.translation.translator

import com.g2pdev.simpletranslator.translation.FirebaseTranslatorProvider
import com.g2pdev.simpletranslator.translation.language.LanguagePair
import io.reactivex.Single
import timber.log.Timber

class FirebaseMLTranslator(
    private val firebaseTranslatorProvider: FirebaseTranslatorProvider
) : Translator {

    override fun translate(languagePair: LanguagePair, text: String): Single<String> {
        return Single.create { emitter ->
            Timber.i("Should translate with parameters $languagePair -> $text")

            val firebaseTranslator = firebaseTranslatorProvider.getTranslator(languagePair)
        }
    }
}