package com.g2pdev.simpletranslator.ui.mvp.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.simpletranslator.interactor.favorite.DeleteFavoriteTranslation
import com.g2pdev.simpletranslator.interactor.favorite.GetFavoriteTranslations
import com.g2pdev.simpletranslator.interactor.misc.CopyTextToClipboard
import com.g2pdev.simpletranslator.ui.mvp.base.BasePresenter
import com.g2pdev.translator.base.extension.schedulersIoToMain
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class FavoritesPresenter : BasePresenter<FavoritesView>() {

    @Inject
    lateinit var getFavoriteTranslations: GetFavoriteTranslations

    @Inject
    lateinit var deleteFavoriteTranslation: DeleteFavoriteTranslation

    @Inject
    lateinit var copyTextToClipboard: CopyTextToClipboard

    init {
        DiHolder.appComponent.inject(this)

        loadFavorites()
    }

    private fun loadFavorites() {
        getFavoriteTranslations
            .exec()
            .schedulersIoToMain()
            .subscribe(viewState::showFavorites, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun delete(favoriteTranslation: FavoriteTranslation) {
        deleteFavoriteTranslation
            .exec(favoriteTranslation)
            .andThen(getFavoriteTranslations.exec())
            .schedulersIoToMain()
            .subscribe(viewState::showFavorites, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun copyText(text: String) {
        copyTextToClipboard
            .exec(text)
            .schedulersIoToMain()
            .subscribe(viewState::showCopiedToClipboard, Timber::e)
            .disposeOnPresenterDestroy()
    }

    fun shareText(text: String) {
        viewState.shareText(text)
    }
}