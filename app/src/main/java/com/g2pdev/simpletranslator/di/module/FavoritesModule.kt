package com.g2pdev.simpletranslator.di.module

import com.g2pdev.simpletranslator.db.FavoriteTranslationsDao
import com.g2pdev.simpletranslator.db.TranslatorDatabase
import com.g2pdev.simpletranslator.interactor.favorite.*
import com.g2pdev.simpletranslator.repository.favorite.FavoriteTranslationsRepository
import com.g2pdev.simpletranslator.repository.favorite.FavoriteTranslationsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoritesModule {

    @Provides
    @Singleton
    fun provideFavoriteTranslationsDao(
        translatorDatabase: TranslatorDatabase
    ): FavoriteTranslationsDao = translatorDatabase.favoriteTranslationsDao()

    @Provides
    @Singleton
    fun provideFavoriteTranslationsRepository(
        favoriteTranslationsDao: FavoriteTranslationsDao
    ): FavoriteTranslationsRepository = FavoriteTranslationsRepositoryImpl(favoriteTranslationsDao)

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
}