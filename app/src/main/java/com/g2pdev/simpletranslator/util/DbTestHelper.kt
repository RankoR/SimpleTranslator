package com.g2pdev.simpletranslator.util

import com.g2pdev.simpletranslator.db.FavoriteTranslationsDao
import io.reactivex.Completable

interface DbTestHelper {
    fun clearFavoritesDatabase(): Completable
}

class DbTestHelperImpl(
    private val favoriteTranslationsDao: FavoriteTranslationsDao
) : DbTestHelper {

    override fun clearFavoritesDatabase(): Completable {
        return favoriteTranslationsDao.deleteAll()
    }
}