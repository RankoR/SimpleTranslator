package com.g2pdev.simpletranslator.ui.mvp.translate

import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.simpletranslator.interactor.favorite.AddOrRemoveFavoriteTranslation
import com.g2pdev.simpletranslator.interactor.favorite.CreateFavoriteTranslation
import com.g2pdev.simpletranslator.interactor.favorite.TranslationIsInFavorites
import com.g2pdev.simpletranslator.interactor.misc.CopyTextToClipboard
import com.g2pdev.translation.translation.interactor.Translate
import com.g2pdev.simpletranslator.interactor.translation.cache.GetLastTextToTranslate
import com.g2pdev.simpletranslator.interactor.translation.cache.GetTranslationLanguagePair
import com.g2pdev.simpletranslator.interactor.translation.cache.SaveLastTextToTranslate
import com.g2pdev.simpletranslator.interactor.translation.cache.SaveTranslationLanguagePair
import com.g2pdev.simpletranslator.interactor.tts.CheckIfTtsLanguageAvailable
import com.g2pdev.simpletranslator.repository.tts.SpeakText
import com.g2pdev.translation.translation.exception.ModelNotDownloadedException
import com.g2pdev.translation.translation.language.LanguagePair
import com.g2pdev.translation.translation.model.TranslationModel
import com.g2pdev.simpletranslator.ui.mvp.base.BasePresenter
import com.g2pdev.translator.base.extension.schedulersIoToMain
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
    lateinit var translationIsInFavorites: TranslationIsInFavorites

    @Inject
    lateinit var createFavoriteTranslation: CreateFavoriteTranslation

    @Inject
    lateinit var addOrRemoveFavoriteTranslation: AddOrRemoveFavoriteTranslation

    @Inject
    lateinit var copyTextToClipboard: CopyTextToClipboard

    @Inject
    lateinit var checkIfTtsLanguageAvailable: CheckIfTtsLanguageAvailable

    @Inject
    lateinit var speakText: SpeakText

    init {
        DiHolder.appComponent.inject(this)
    }

    override fun attachView(view: TranslateView?) {
        super.attachView(view)

        loadTranslationLanguagesAndReTranslate()
        checkIfTtsAvailable()
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
            .subscribe({
                checkIfTtsAvailable()
                loadTranslationLanguagesAndReTranslate()
            }, Timber::e)
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
            .subscribe({
                checkIfTtsAvailable()
                loadTranslationLanguagesAndReTranslate()
            }, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun translate(text: String) {
        if (text.isBlank()) {
            viewState.showTranslation("")
            viewState.showTargetActions(false)

            return
        }

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
                viewState.showTargetActions(translatedText.isNotBlank())

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

    fun addToFavorites(sourceText: String, targetText: String) {
        createFavoriteTranslation
            .exec(sourceText, targetText)
            .flatMap(addOrRemoveFavoriteTranslation::exec)
            .schedulersIoToMain()
            .doOnSubscribe { viewState.enableAddToFavorites(false) }
            .doFinally { viewState.enableAddToFavorites(true) }
            .subscribe({
                Timber.i("Added to favorites: $it")

                viewState.showTranslationIsInFavorites(it)
            }, Timber::e)
            .disposeOnPresenterDestroy()
    }

    private fun checkIfTranslationIsInFavorites(sourceText: String, targetText: String) {
        createFavoriteTranslation
            .exec(sourceText, targetText)
            .flatMap(translationIsInFavorites::exec)
            .schedulersIoToMain()
            .doOnSubscribe { viewState.enableAddToFavorites(false) }
            .doFinally { viewState.enableAddToFavorites(true) }
            .subscribe(viewState::showTranslationIsInFavorites, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun copyToClipboard(text: String) {
        copyTextToClipboard
            .exec(text)
            .schedulersIoToMain()
            .subscribe(viewState::showCopiedToClipboard, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun share(text: String) {
        viewState.shareText(text)
    }

    private fun checkIfTtsAvailable() {
        getTranslationLanguagePair
            .exec()
            .map { it.target.language }
            .flatMapCompletable(checkIfTtsLanguageAvailable::exec)
            .schedulersIoToMain()
            .doOnSubscribe { viewState.enableTts(false) }
            .doOnError { viewState.enableTts(false) }
            .doOnComplete { viewState.enableTts(true) }
            .subscribe({}, Timber::w)
            .disposeOnPresenterDestroy()
    }

    fun speakText(text: String) {
        getTranslationLanguagePair
            .exec()
            .map { it.target.language }
            .flatMapCompletable { speakText.exec(it, text) }
            .schedulersIoToMain()
            .doOnSubscribe {
                viewState.enableTts(false)
                viewState.showTtsSpeaking(true)
            }
            .doFinally {
                viewState.enableTts(true)
                viewState.showTtsSpeaking(false)
            }
            .subscribe({}, Timber::w)
            .disposeOnPresenterDestroy()
    }

    fun clearSourceText() {
        viewState.clearSourceText()
    }

    fun openDownloadLanguagesClick() {
        viewState.openDownloadModelsScreen()
    }

}