package com.g2pdev.simpletranslator.ui.mvp.language

import com.g2pdev.translation.translation.model.TranslationModelWithState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface TranslationModelsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showModels(translationModels: List<TranslationModelWithState>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(t: Throwable)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun notifyModelSelected(translationModelWithState: TranslationModelWithState)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showModelNotDownloadedSelectionError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun close()
}