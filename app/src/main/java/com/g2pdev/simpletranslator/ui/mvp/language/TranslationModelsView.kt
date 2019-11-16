package com.g2pdev.simpletranslator.ui.mvp.language

import com.g2pdev.simpletranslator.translation.model.TranslationModelWithState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface TranslationModelsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showModels(translationModels: List<TranslationModelWithState>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(t: Throwable)
}