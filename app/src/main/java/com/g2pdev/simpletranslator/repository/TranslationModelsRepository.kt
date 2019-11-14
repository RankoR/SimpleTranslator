package com.g2pdev.simpletranslator.repository

import com.g2pdev.simpletranslator.translation.language.FirebaseLanguageConverter
import com.g2pdev.simpletranslator.translation.language.Language
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateRemoteModel
import io.reactivex.Completable
import io.reactivex.Single

interface TranslationModelsRepository {
    fun listAvailableModels(): Single<Collection<Language>>
    fun listDownloadedModels(): Single<Collection<Language>>
    fun isModelDownloaded(language: Language): Single<Boolean>
    fun downloadModel(language: Language): Completable
}

class FirebaseTranslationModelsRepository(
    private val firebaseModelManager: FirebaseModelManager,
    private val firebaseLanguageConverter: FirebaseLanguageConverter
) : TranslationModelsRepository {

    override fun listAvailableModels(): Single<Collection<Language>> {
        val languages = Language
            .values()
            .filter { !it.isUnknown() }
            .toSet()

        return Single.just(languages)
    }

    override fun listDownloadedModels(): Single<Collection<Language>> {
        return Single.create { emitter ->
            firebaseModelManager
                .getDownloadedModels(FirebaseTranslateRemoteModel::class.java)
                .addOnSuccessListener { models ->
                    val convertedModels = models.map { firebaseLanguageConverter.convertFirebaseCodeToLanguage(it.language) }

                    if (!emitter.isDisposed) {
                        emitter.onSuccess(convertedModels)
                    }
                }
                .addOnFailureListener {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
    }

    override fun isModelDownloaded(language: Language): Single<Boolean> {
        return Single.create { emitter ->
            val model = language.toFirebaseModel()

            firebaseModelManager
                .isModelDownloaded(model)
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

    override fun downloadModel(language: Language): Completable {
        return Completable.create { emitter ->
            val model = language.toFirebaseModel()

            firebaseModelManager
                .download(model, getModelDownloadConditions())
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onComplete()
                    }
                }
                .addOnFailureListener {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
    }

    private fun getModelDownloadConditions(): FirebaseModelDownloadConditions {
        // TODO: Move to app's settings
        return FirebaseModelDownloadConditions.Builder().build()
    }

    private fun Language.toFirebaseModel(): FirebaseTranslateRemoteModel {
        val languageCode = firebaseLanguageConverter.convertLanguageToFirebaseCode(this)

        return FirebaseTranslateRemoteModel.Builder(languageCode).build()
    }

}