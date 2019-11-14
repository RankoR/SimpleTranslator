package com.g2pdev.simpletranslator.ui.mvp.translate

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface TranslateView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(loading: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTranslation(text: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(e: Throwable)
}