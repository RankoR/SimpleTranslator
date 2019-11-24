package com.g2pdev.simpletranslator.di.module

import android.content.Context
import androidx.room.Room
import com.g2pdev.simpletranslator.db.FavoriteTranslationsDao
import com.g2pdev.simpletranslator.db.FavoriteTranslationsDatabase
import com.g2pdev.simpletranslator.interactor.favorite.*
import com.g2pdev.simpletranslator.interactor.translation.cache.GetTranslationLanguagePair
import com.g2pdev.simpletranslator.repository.favorite.FavoriteTranslationsRepository
import com.g2pdev.simpletranslator.repository.favorite.FavoriteTranslationsRepositoryImpl
import com.g2pdev.simpletranslator.util.DbTestHelper
import com.g2pdev.simpletranslator.util.DbTestHelperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class FavoritesModule {

    @Provides
    @Singleton
    open fun provideFavoritesDatabase(
        context: Context
    ): FavoriteTranslationsDatabase {
        return Room
            .databaseBuilder(
                context,
                FavoriteTranslationsDatabase::class.java,
                favoritesDbName
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteTranslationsDao(
        favoriteTranslationsDatabase: FavoriteTranslationsDatabase
    ): FavoriteTranslationsDao = favoriteTranslationsDatabase.favoriteTranslationsDao()

    @Provides
    @Singleton
    fun provideFavoriteTranslationsRepository(
        favoriteTranslationsDao: FavoriteTranslationsDao
    ): FavoriteTranslationsRepository = FavoriteTranslationsRepositoryImpl(favoriteTranslationsDao)

    @Provides
    @Singleton
    fun provideCreateFavoriteTranslation(
        getTranslationLanguagePair: GetTranslationLanguagePair
    ): CreateFavoriteTranslation = CreateFavoriteTranslationImpl(getTranslationLanguagePair)

    @Provides
    @Singleton
    fun provideGetFavoriteTranslations(
        favoriteTranslationsRepository: FavoriteTranslationsRepository
    ): GetFavoriteTranslations = GetFavoriteTranslationsImpl(favoriteTranslationsRepository)

    @Provides
    @Singleton
    fun provideAddFavoriteTranslation(
        favoriteTranslationsRepository: FavoriteTranslationsRepository
    ): AddFavoriteTranslation = AddFavoriteTranslationImpl(favoriteTranslationsRepository)

    @Provides
    @Singleton
    fun provideDeleteFavoriteTranslation(
        favoriteTranslationsRepository: FavoriteTranslationsRepository
    ): DeleteFavoriteTranslation = DeleteFavoriteTranslationImpl(favoriteTranslationsRepository)

    @Provides
    @Singleton
    fun provideTranslationIsInFavorites(
        favoriteTranslationsRepository: FavoriteTranslationsRepository
    ): TranslationIsInFavorites = TranslationIsInFavoritesImpl(favoriteTranslationsRepository)

    @Provides
    @Singleton
    fun provideAddOrRemoveFavoriteTranslation(
        translationIsInFavorites: TranslationIsInFavorites,
        addFavoriteTranslation: AddFavoriteTranslation,
        deleteFavoriteTranslation: DeleteFavoriteTranslation
    ): AddOrRemoveFavoriteTranslation = AddOrRemoveFavoriteTranslationImpl(translationIsInFavorites, addFavoriteTranslation, deleteFavoriteTranslation)

    @Provides
    @Singleton
    fun provideDbTestHelper(
        favoriteTranslationsDao: FavoriteTranslationsDao
    ): DbTestHelper = DbTestHelperImpl(favoriteTranslationsDao)


    private companion object {
        private const val favoritesDbName = "favorites"
    }
}