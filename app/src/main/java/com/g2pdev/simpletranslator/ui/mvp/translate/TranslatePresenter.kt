package com.g2pdev.simpletranslator.ui.mvp.translate

import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.simpletranslator.interactor.translation.Translate
import com.g2pdev.simpletranslator.interactor.translation.cache.GetLastTextToTranslate
import com.g2pdev.simpletranslator.interactor.translation.cache.GetTranslationLanguagePair
import com.g2pdev.simpletranslator.interactor.translation.cache.SaveLastTextToTranslate
import com.g2pdev.simpletranslator.interactor.translation.cache.SaveTranslationLanguagePair
import com.g2pdev.simpletranslator.translation.exception.ModelNotDownloadedException
import com.g2pdev.simpletranslator.translation.language.LanguagePair
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

    fun onLanguagePairChanged(languagePair: LanguagePair) {
        // TODO: Call this from fragment

        saveTranslationLanguagePair
            .exec(languagePair)
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
                // TODO: Re-translate
            }, Timber::e)
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