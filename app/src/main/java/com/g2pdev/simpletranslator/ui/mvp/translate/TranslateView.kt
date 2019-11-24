package com.g2pdev.simpletranslator.ui.mvp.translate

import com.g2pdev.simpletranslator.translation.language.LanguagePair
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface TranslateView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun disableInputs(disable: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun enableAddToFavorites(enable: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun disableLanguageChange(disable: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLanguagePair(languagePair: LanguagePair)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSourceLanguageChooser()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTargetLanguageChooser()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setTextToTranslate(text: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(loading: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTranslation(text: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(e: Throwable)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showModelRequired(languagePair: LanguagePair)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTranslationIsInFavorites(isInFavorites: Boolean)
}