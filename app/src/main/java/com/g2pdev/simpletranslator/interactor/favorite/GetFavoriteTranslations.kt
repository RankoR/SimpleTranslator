package com.g2pdev.simpletranslator.interactor.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.repository.favorite.FavoriteTranslationsRepository
import io.reactivex.Single

interface GetFavoriteTranslations {
    fun exec(): Single<List<FavoriteTranslation>>
}

class GetFavoriteTranslationsImpl(
    private val favoriteTranslationsRepository: FavoriteTranslationsRepository
) : GetFavoriteTranslations {

    override fun exec(): Single<List<FavoriteTranslation>> {
        return favoriteTranslationsRepository.getTranslations()
    }
}