package com.g2pdev.simpletranslator.ui.mvp.translate

import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.simpletranslator.interactor.translation.Translate
import com.g2pdev.simpletranslator.interactor.translation.cache.GetLastTextToTranslate
import com.g2pdev.simpletranslator.interactor.translation.cache.SaveLastTextToTranslate
import com.g2pdev.simpletranslator.translation.language.Language
import com.g2pdev.simpletranslator.translation.language.LanguagePair
import com.g2pdev.simpletranslator.ui.mvp.base.BasePresenter
import com.g2pdev.simpletranslator.util.schedulersIoToMain
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class TranslatePresenter : BasePresenter<TranslateView>() {

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

        getLastTextToTranslate
            .exec()
            .schedulersIoToMain()
            .subscribe(viewState::setTextToTranslate, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun translate(text: String) {
        saveLastTextToTranslate
            .exec(text)
            .andThen(translate.exec(LanguagePair(Language.EN, Language.RU), text))
            .schedulersIoToMain()
            .subscribe({ translatedText ->
                Timber.i("Translated: $translatedText")

                viewState.showTranslation(translatedText)
            }, { e ->
                Timber.e("Failed to translate text: $e")

                viewState.showError(e)
            })
            .disposeOnPresenterDestroy()

    }

}