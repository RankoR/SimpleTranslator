package com.g2pdev.simpletranslator.favorite

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.simpletranslator.translation.language.Language
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoritesTest {

    private val dbTestHelper by lazy { DiHolder.appComponent.dbTestHelper }
    private val getFavoriteTranslations by lazy { DiHolder.appComponent.getFavoriteTranslations }
    private val addFavoriteTranslation by lazy { DiHolder.appComponent.addFavoriteTranslation }
    private val deleteFavoriteTranslation by lazy { DiHolder.appComponent.deleteFavoriteTranslation }
    private val translationIsInFavorites by lazy { DiHolder.appComponent.translationIsInFavorites }

    @Before
    fun setUp() {
        clearFavorites()
    }

    @After
    fun tearDown() {
        clearFavorites()
    }

    private fun clearFavorites() {
        dbTestHelper.clearFavoritesDatabase().blockingGet()
    }

    private fun getCurrentFavoriteCount(): Int = getFavoriteTranslations.exec().blockingGet().size

    @Test
    fun testAddFavorite() {
        clearFavorites()

        addFavoriteTranslation
            .exec(
                FavoriteTranslation(
                    sourceLanguageCode = Language.EN.name,
                    targetLanguageCode = Language.RU.name,
                    sourceText = "test en",
                    targetText = "test ru"
                )
            )
            .test()
            .assertComplete()

        Assert.assertEquals(1, getCurrentFavoriteCount())
    }

    @Test
    fun testDeleteFromFavorites() {
        clearFavorites()

        val favoriteTranslation = FavoriteTranslation(
            sourceLanguageCode = Language.EN.name,
            targetLanguageCode = Language.RU.name,
            sourceText = "test en",
            targetText = "test ru"
        )

        addFavoriteTranslation
            .exec(favoriteTranslation)
            .test()
            .assertComplete()

        Assert.assertEquals(1, getCurrentFavoriteCount())

        deleteFavoriteTranslation
            .exec(favoriteTranslation)
            .test()
            .assertComplete()

        Assert.assertEquals(0, getCurrentFavoriteCount())
    }

}