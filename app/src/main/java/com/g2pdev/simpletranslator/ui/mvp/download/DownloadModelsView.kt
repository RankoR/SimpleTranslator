package com.g2pdev.simpletranslator.ui.mvp.download

import com.g2pdev.simpletranslator.translation.model.TranslationModelWithState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface DownloadModelsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showModels(translationModels: List<TranslationModelWithState>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(t: Throwable)
}