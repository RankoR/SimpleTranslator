package com.g2pdev.translation.translation.repository

import com.g2pdev.translation.translation.language.FirebaseLanguageConverter
import com.g2pdev.translation.translation.language.Language
import com.g2pdev.translation.translation.model.TranslationModel
import com.g2pdev.translation.translation.util.LanguageNameProvider
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateRemoteModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

interface TranslationModelsRepository {
    fun listAvailableModels(): Single<Collection<TranslationModel>>

    fun listDownloadedModels(): Single<Collection<TranslationModel>>

    fun isModelDownloaded(model: TranslationModel): Single<Boolean>
    fun isModelDownloaded(language: Language): Single<Boolean>

    fun downloadModel(model: TranslationModel): Completable

    fun getDownloadingModels(): Single<Collection<TranslationModel>>

    fun getDownloadingStateChangedObservable(): Observable<Unit>

    fun deleteModel(model: TranslationModel): Completable
}

class FirebaseTranslationModelsRepository(
    private val firebaseModelManager: FirebaseModelManager,
    private val firebaseLanguageConverter: FirebaseLanguageConverter,
    private val languageNameProvider: LanguageNameProvider
) : TranslationModelsRepository {

    private val downloadingStateChangedPublishSubject = PublishSubject.create<Unit>()

    private val downloadingLanguages = mutableSetOf<TranslationModel>()

    override fun listAvailableModels(): Single<Collection<TranslationModel>> {
        val languages = Language
            .values()
            .filter { !it.isUnknown() }
            .map { it.toTranslationModel() }
            .toSet()

        return Single.just(languages)
    }

    override fun listDownloadedModels(): Single<Collection<TranslationModel>> {
        return Single.create { emitter ->
            firebaseModelManager
                .getDownloadedModels(FirebaseTranslateRemoteModel::class.java)
                .addOnSuccessListener { models ->
                    val convertedModels = models
                        .map { firebaseLanguageConverter.convertFirebaseCodeToLanguage(it.language) }
                        .map { it.toTranslationModel() }

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

    override fun isModelDownloaded(model: TranslationModel): Single<Boolean> {
        return Single.create { emitter ->
            val firebaseModel = model.language.toFirebaseModel()

            firebaseModelManager
                .isModelDownloaded(firebaseModel)
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

    override fun isModelDownloaded(language: Language): Single<Boolean> {
        return isModelDownloaded(language.toTranslationModel())
    }

    override fun downloadModel(model: TranslationModel): Completable {
        if (downloadingLanguages.contains(model)) {
            return Completable.error(IllegalStateException("Already downloading this model"))
        }

        Timber.i("Downloading model: $model")

        return Completable.create { emitter ->
            notifyLanguageDownloading(model)

            val firebaseModel = model.language.toFirebaseModel()

            firebaseModelManager
                .download(firebaseModel, getModelDownloadConditions())
                .addOnSuccessListener {
                    notifyLanguageNotDownloading(model)

                    if (!emitter.isDisposed) {
                        emitter.onComplete()
                    }
                }
                .addOnFailureListener {
                    notifyLanguageNotDownloading(model)

                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
                .addOnCanceledListener {
                    notifyLanguageNotDownloading(model)
                }
        }
    }

    override fun getDownloadingModels(): Single<Collection<TranslationModel>> {
        return Single.just(downloadingLanguages)
    }

    override fun getDownloadingStateChangedObservable(): Observable<Unit> {
        return downloadingStateChangedPublishSubject
    }

    override fun deleteModel(model: TranslationModel): Completable {
        return Completable.create { emitter ->
            val firebaseModel = model.language.toFirebaseModel()

            firebaseModelManager
                .deleteDownloadedModel(firebaseModel)
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

    private fun notifyLanguageDownloading(model: TranslationModel) {
        downloadingLanguages.add(model)
        notifyDownloadingStateChanged()
    }

    private fun notifyLanguageNotDownloading(model: TranslationModel) {
        downloadingLanguages.remove(model)
        notifyDownloadingStateChanged()
    }

    private fun notifyDownloadingStateChanged() {
        downloadingStateChangedPublishSubject.onNext(Unit)
    }

    private fun Language.toFirebaseModel(): FirebaseTranslateRemoteModel {
        val languageCode = firebaseLanguageConverter.convertLanguageToFirebaseCode(this)

        return FirebaseTranslateRemoteModel.Builder(languageCode).build()
    }

    private fun Language.toTranslationModel(ordering: Int = 0): TranslationModel {
        return TranslationModel(
            this,
            languageNameProvider.getNameForLanguage(this),
            ordering
        )
    }

}