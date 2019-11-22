package com.g2pdev.simpletranslator.interactor.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.repository.favorite.FavoriteTranslationsRepository
import io.reactivex.Completable

interface AddFavoriteTranslation {
    fun exec(favoriteTranslation: FavoriteTranslation): Completable
}

class AddFavoriteTranslationImpl(
    private val favoriteTranslationsRepository: FavoriteTranslationsRepository
) : AddFavoriteTranslation {

    override fun exec(favoriteTranslation: FavoriteTranslation): Completable {
        return favoriteTranslationsRepository.addTranslation(favoriteTranslation)
    }
}