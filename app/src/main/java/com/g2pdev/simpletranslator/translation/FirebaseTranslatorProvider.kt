package com.g2pdev.simpletranslator.translation

import com.g2pdev.simpletranslator.translation.language.LanguageConverter
import com.g2pdev.simpletranslator.translation.language.LanguagePair
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
    private val languageConverter: LanguageConverter
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
                    .setSourceLanguage(languageConverter.convertLanguageToFirebaseCode(languagePair.source))
                    .setTargetLanguage(languageConverter.convertLanguageToFirebaseCode(languagePair.target))
                    .build()

                val translator = FirebaseNaturalLanguage.getInstance().getTranslator(options)

                translators[languagePair] = translator

                translator
            }
        }
    }

}