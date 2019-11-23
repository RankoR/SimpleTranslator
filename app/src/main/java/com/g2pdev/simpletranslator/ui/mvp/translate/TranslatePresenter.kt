package com.g2pdev.simpletranslator.ui.mvp.translate

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.simpletranslator.interactor.favorite.AddFavoriteTranslation
import com.g2pdev.simpletranslator.interactor.favorite.DeleteFavoriteTranslation
import com.g2pdev.simpletranslator.interactor.favorite.TranslationIsInFavorites
import com.g2pdev.simpletranslator.interactor.translation.Translate
import com.g2pdev.simpletranslator.interactor.translation.cache.GetLastTextToTranslate
import com.g2pdev.simpletranslator.interactor.translation.cache.GetTranslationLanguagePair
import com.g2pdev.simpletranslator.interactor.translation.cache.SaveLastTextToTranslate
import com.g2pdev.simpletranslator.interactor.translation.cache.SaveTranslationLanguagePair
import com.g2pdev.simpletranslator.translation.exception.ModelNotDownloadedException
import com.g2pdev.simpletranslator.translation.language.LanguagePair
import com.g2pdev.simpletranslator.translation.model.TranslationModel
import com.g2pdev.simpletranslator.ui.mvp.base.BasePresenter
import com.g2pdev.simpletranslator.util.schedulersIoToMain
import io.reactivex.Single
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class TranslatePresenter : BasePresenter<TranslateView>() {

    @Inject
    lateinit var getTranslationLanguagePair: GetTranslationLanguagePair

    @Inject
    lateinit var saveTranslationLanguagePair: SaveTranslationLanguagePair

    @Inject
    lateinit var getLastTextToTranslate: GetLastTextToTranslate

    @Inject
    lateinit var saveLastTextToTranslate: SaveLastTextToTranslate

    @Inject
    lateinit var translate: Translate

    @Inject
    lateinit var addFavoriteTranslation: AddFavoriteTranslation

    @Inject
    lateinit var deleteFavoriteTranslation: DeleteFavoriteTranslation

    @Inject
    lateinit var translationIsInFavorites: TranslationIsInFavorites

    init {
        DiHolder.appComponent.inject(this)
    }

    override fun attachView(view: TranslateView?) {
        super.attachView(view)

        loadTranslationLanguagesAndReTranslate()
    }

    fun loadTranslationLanguagesAndReTranslate() {
        getTranslationLanguagePair
            .exec()
            .doOnSuccess(viewState::showLanguagePair)
            .flatMap { getLastTextToTranslate.exec() }
            .schedulersIoToMain()
            .doOnSubscribe {
                viewState.disableInputs(true)
                viewState.disableLanguageChange(true)
            }
            .doFinally {
                viewState.disableInputs(false)
                viewState.disableLanguageChange(false)
            }
            .subscribe(viewState::setTextToTranslate, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun onSourceLanguageChangeClick() {
        viewState.showSourceLanguageChooser()
    }

    fun onTargetLanguageChangeClick() {
        viewState.showTargetLanguageChooser()
    }

    fun onSwapLanguagesClick() {
        getTranslationLanguagePair
            .exec()
            .map { languagePair ->
                if (languagePair.source.isAuto || languagePair.target.isAuto) {
                    throw IllegalStateException("Can not swap when one of languages is auto")
                } else {
                    LanguagePair(
                        source = languagePair.target,
                        target = languagePair.source
                    )
                }
            }
            .flatMapCompletable(saveTranslationLanguagePair::exec)
            .schedulersIoToMain()
            .doOnSubscribe {
                viewState.disableInputs(true)
                viewState.disableLanguageChange(true)
            }
            .doFinally {
                viewState.disableInputs(false)
                viewState.disableLanguageChange(false)
            }
            .subscribe(::loadTranslationLanguagesAndReTranslate, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun onSourceLanguageChanged(sourceLanguage: TranslationModel) {
        getTranslationLanguagePair
            .exec()
            .flatMapCompletable {
                saveTranslationLanguagePair
                    .exec(it.copyWithReplacedSourceLanguage(sourceLanguage))
            }
            .subscribe(::loadTranslationLanguagesAndReTranslate, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun onTargetLanguageChanged(targetLanguage: TranslationModel) {
        getTranslationLanguagePair
            .exec()
            .flatMapCompletable {
                saveTranslationLanguagePair
                    .exec(it.copyWithReplacedTargetLanguage(targetLanguage))
            }
            .subscribe(::loadTranslationLanguagesAndReTranslate, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun translate(text: String) {
        saveLastTextToTranslate
            .exec(text)
            .andThen(getTranslationLanguagePair.exec())
            .flatMap { translate.exec(it, text) }
            .schedulersIoToMain()
            .doOnSubscribe {
                viewState.disableLanguageChange(true)
            }
            .doFinally {
                viewState.disableLanguageChange(false)
            }
            .subscribe({ translatedText ->
                Timber.i("Translated: $translatedText")

                viewState.showTranslation(translatedText)
                checkIfTranslationIsInFavorites(text, translatedText)
            }, { e ->
                if (e is ModelNotDownloadedException) {
                    Timber.i("Model not downloaded yet")

                    viewState.showModelRequired(e.languagePair)
                } else {
                    Timber.e(e)

                    viewState.showError(e)
                }
            })
            .disposeOnPresenterDestroy()
    }

    private fun createFavoriteTranslation(sourceText: String, targetText: String): Single<FavoriteTranslation> {
        return getTranslationLanguagePair
            .exec()
            .map { languagePair ->
                FavoriteTranslation(
                    sourceLanguageCode = languagePair.source.name,
                    targetLanguageCode = languagePair.target.name,
                    sourceText = sourceText,
                    targetText = targetText
                )
            }
    }

    fun addToFavorites(sourceText: String, targetText: String) {
        createFavoriteTranslation(sourceText, targetText)
            .flatMap(translationIsInFavorites::exec)
            .flatMap { isInFavorites ->
                if (isInFavorites) {
                    createFavoriteTranslation(sourceText, targetText).flatMapCompletable(deleteFavoriteTranslation::exec).andThen(Single.just(false))
                } else {
                    createFavoriteTranslation(sourceText, targetText).flatMapCompletable(addFavoriteTranslation::exec).andThen(Single.just(true))
                }
            }
            .schedulersIoToMain()
            .doOnSubscribe { viewState.enableAddToFavorites(false) }
            .doFinally { viewState.enableAddToFavorites(true) }
            .subscribe({
                Timber.i("Added to favorites: $it")

                viewState.showAddedToFavorites()
            }, Timber::e)
            .disposeOnPresenterDestroy()
    }

    private fun checkIfTranslationIsInFavorites(sourceText: String, targetText: String) {
        createFavoriteTranslation(sourceText, targetText)
            .flatMap(translationIsInFavorites::exec)
            .schedulersIoToMain()
            .doOnSubscribe { viewState.enableAddToFavorites(false) }
            .doFinally { viewState.enableAddToFavorites(true) }
            .subscribe(viewState::showTranslationIsInFavorites, Timber::e)
            .disposeOnPresenterDestroy()
    }

}