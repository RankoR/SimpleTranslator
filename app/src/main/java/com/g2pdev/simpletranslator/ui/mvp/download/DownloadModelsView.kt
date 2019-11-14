package com.g2pdev.simpletranslator.ui.mvp.download

import com.g2pdev.simpletranslator.repository.ModelWithState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface DownloadModelsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showModels(models: List<ModelWithState>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(t: Throwable)
}