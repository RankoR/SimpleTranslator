package com.g2pdev.simpletranslator.ui.mvp.download

import com.g2pdev.simpletranslator.repository.ModelState
import com.g2pdev.simpletranslator.translation.language.Language
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface DownloadModelsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showModels(languages: Map<Language, ModelState>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(t: Throwable)
}