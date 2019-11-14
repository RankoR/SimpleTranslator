package com.g2pdev.simpletranslator.ui.mvp.translate

import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.simpletranslator.interactor.translation.Translate
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
    lateinit var translate: Translate

    init {
        DiHolder.appComponent.inject(this)
    }

    fun translate(text: String) {
        translate
            .exec(
                LanguagePair(Language.EN, Language.RU),
                text
            )
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