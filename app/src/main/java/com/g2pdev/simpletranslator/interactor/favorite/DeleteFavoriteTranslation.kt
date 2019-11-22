package com.g2pdev.simpletranslator.interactor.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.repository.favorite.FavoriteTranslationsRepository
import io.reactivex.Completable

interface DeleteFavoriteTranslation {
    fun exec(favoriteTranslation: FavoriteTranslation): Completable
}

class DeleteFavoriteTranslationImpl(
    private val favoriteTranslationsRepository: FavoriteTranslationsRepository
) : DeleteFavoriteTranslation {

    override fun exec(favoriteTranslation: FavoriteTranslation): Completable {
        return favoriteTranslationsRepository.deleteTranslation(favoriteTranslation)
    }
}