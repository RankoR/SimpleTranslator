package com.g2pdev.translation.translation.translator

import com.g2pdev.translation.translation.language.LanguagePair
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import io.reactivex.Single
import timber.log.Timber

class FirebaseMLTranslator(
    private val firebaseTranslatorProvider: FirebaseTranslatorProvider
) : Translator {

    override fun translate(languagePair: LanguagePair, text: String): Single<String> {
        Timber.i("Should translate with parameters $languagePair -> $text")

        return firebaseTranslatorProvider
            .getTranslator(languagePair)
            .flatMap { translate(it, text) }
    }

    private fun translate(firebaseTranslator: FirebaseTranslator, text: String): Single<String> {
        return Single.create { emitter ->
            firebaseTranslator
                .translate(text)
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(it)
                    }
                }
                .addOnFailureListener {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
    }
}