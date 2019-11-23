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

        val favoriteTranslation1 = FavoriteTranslation(
            sourceLanguageCode = Language.EN.name,
            targetLanguageCode = Language.RU.name,
            sourceText = "test en",
            targetText = "test ru"
        )

        val favoriteTranslation2 = FavoriteTranslation(
            sourceLanguageCode = Language.ES.name,
            targetLanguageCode = Language.BE.name,
            sourceText = "test en",
            targetText = "test ru"
        )

        addFavoriteTranslation
            .exec(favoriteTranslation1)
            .test()
            .assertComplete()

        addFavoriteTranslation
            .exec(favoriteTranslation2)
            .test()
            .assertComplete()

        Assert.assertEquals(2, getCurrentFavoriteCount())

        deleteFavoriteTranslation
            .exec(favoriteTranslation1)
            .test()
            .assertComplete()

        Assert.assertEquals(1, getCurrentFavoriteCount())

        deleteFavoriteTranslation
            .exec(favoriteTranslation2)
            .test()
            .assertComplete()

        Assert.assertEquals(0, getCurrentFavoriteCount())
    }

    @Test
    fun testGetFavorites() {
        clearFavorites()

        val favoriteTranslation1 = FavoriteTranslation(
            sourceLanguageCode = Language.EN.name,
            targetLanguageCode = Language.RU.name,
            sourceText = "test en",
            targetText = "test ru"
        )

        val favoriteTranslation2 = FavoriteTranslation(
            sourceLanguageCode = Language.ES.name,
            targetLanguageCode = Language.EN.name,
            sourceText = "test es",
            targetText = "test en"
        )

        val favoriteTranslation3 = FavoriteTranslation(
            sourceLanguageCode = Language.UK.name,
            targetLanguageCode = Language.BE.name,
            sourceText = "test uk",
            targetText = "test be"
        )

        addFavoriteTranslation
            .exec(favoriteTranslation1)
            .test()
            .assertComplete()

        addFavoriteTranslation
            .exec(favoriteTranslation2)
            .test()
            .assertComplete()

        addFavoriteTranslation
            .exec(favoriteTranslation3)
            .test()
            .assertComplete()

        getFavoriteTranslations
            .exec()
            .test()
            .assertComplete()
            .assertOf {
                val gotTranslations = it.values().first().toTypedArray()
                val expectedTranslations = arrayOf(favoriteTranslation3, favoriteTranslation2, favoriteTranslation1)

                Assert.assertTrue("Translations not equal", gotTranslations.contentDeepEquals(expectedTranslations))
            }
    }

    @Test
    fun testIsInFavorites() {
        clearFavorites()

        val favoriteTranslation = FavoriteTranslation(
            sourceLanguageCode = Language.EN.name,
            targetLanguageCode = Language.RU.name,
            sourceText = "test en",
            targetText = "test ru"
        )

        val nonExistentFavoriteTranslation = FavoriteTranslation(
            sourceLanguageCode = Language.ES.name,
            targetLanguageCode = Language.BE.name,
            sourceText = "test en",
            targetText = "test ru"
        )

        // Not yet exists
        translationIsInFavorites
            .exec(favoriteTranslation)
            .test()
            .assertValue(false)

        translationIsInFavorites
            .exec(nonExistentFavoriteTranslation)
            .test()
            .assertValue(false)

        addFavoriteTranslation
            .exec(favoriteTranslation)
            .test()
            .assertComplete()

        // Now exists
        translationIsInFavorites
            .exec(favoriteTranslation)
            .test()
            .assertValue(true)

        translationIsInFavorites
            .exec(nonExistentFavoriteTranslation)
            .test()
            .assertValue(false)

        deleteFavoriteTranslation
            .exec(favoriteTranslation)
            .test()
            .assertComplete()

        // Now not exists again
        translationIsInFavorites
            .exec(favoriteTranslation)
            .test()
            .assertValue(false)
    }

}