package com.g2pdev.simpletranslator.interactor.favorite

import com.g2pdev.simpletranslator.db.FavoriteTranslation
import io.reactivex.Single

interface AddOrRemoveFavoriteTranslation {
    /**
     * @return true if added, false if removed
     */
    fun exec(favoriteTranslation: FavoriteTranslation): Single<Boolean>
}

class AddOrRemoveFavoriteTranslationImpl(
    private val translationIsInFavorites: TranslationIsInFavorites,
    private val addFavoriteTranslation: AddFavoriteTranslation,
    private val deleteFavoriteTranslation: DeleteFavoriteTranslation
) : AddOrRemoveFavoriteTranslation {

    override fun exec(favoriteTranslation: FavoriteTranslation): Single<Boolean> {
        return translationIsInFavorites
            .exec(favoriteTranslation)
            .flatMap { translationIsInFavorites ->
                if (translationIsInFavorites) {
                    deleteFavoriteTranslation.exec(favoriteTranslation).andThen(Single.just(false))
                } else {
                    addFavoriteTranslation.exec(favoriteTranslation).andThen(Single.just(true))
                }
            }
    }
}