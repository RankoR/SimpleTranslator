package com.g2pdev.simpletranslator.ui.mvp.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface FavoritesView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showFavorites(favorites: List<FavoriteTranslation>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCopiedToClipboard()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun shareText(text: String)
}