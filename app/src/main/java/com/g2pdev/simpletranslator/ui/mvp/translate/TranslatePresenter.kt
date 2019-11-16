package com.g2pdev.simpletranslator.ui.mvp.translate

import com.g2pdev.simpletranslator.di.DiHolder
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
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class TranslatePresenter : BasePresenter<TranslateView>() {

    // TODO: Available languages

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

    init {
        DiHolder.appComponent.inject(this)
    }

    override fun attachView(view: TranslateView?) {
        super.attachView(view)

        loadTranslationLanguagesAndReTranslate()
    }

    private fun loadTranslationLanguagesAndReTranslate() {
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
        loadTranslationLanguagesAndReTranslate()
    }

    fun onTargetLanguageChanged(targetLanguage: TranslationModel) {
        loadTranslationLanguagesAndReTranslate()
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
            }, { e ->
                Timber.e(e)

                if (e is ModelNotDownloadedException) {
                    viewState.showModelRequired(e.languagePair)
                } else {
                    viewState.showError(e)
                }
            })
            .disposeOnPresenterDestroy()
    }

}