package com.g2pdev.translation.translation.translator

import com.g2pdev.translation.translation.language.FirebaseLanguageConverter
import com.g2pdev.translation.translation.language.LanguagePair
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import io.reactivex.Single
import timber.log.Timber

interface FirebaseTranslatorProvider {
    fun getTranslator(languagePair: LanguagePair): Single<FirebaseTranslator>
}

class FirebaseTranslatorProviderImpl(
    private val firebaseNaturalLanguage: FirebaseNaturalLanguage,
    private val firebaseLanguageConverter: FirebaseLanguageConverter
) : FirebaseTranslatorProvider {

    private val translators = mutableMapOf<LanguagePair, FirebaseTranslator>()

    override fun getTranslator(languagePair: LanguagePair): Single<FirebaseTranslator> {
        Timber.i("Requested translator for language pair $languagePair")

        val existingTranslator = translators[languagePair]

        return if (existingTranslator != null) {
            Timber.i("Found existing translator")

            Single.just(existingTranslator)
        } else {
            Single.fromCallable {
                Timber.i("Creating new translator")

                val options = FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(firebaseLanguageConverter.convertLanguageToFirebaseCode(languagePair.source.language))
                    .setTargetLanguage(firebaseLanguageConverter.convertLanguageToFirebaseCode(languagePair.target.language))
                    .build()

                val translator = firebaseNaturalLanguage.getTranslator(options)

                translators[languagePair] = translator

                translator
            }
        }
    }

}