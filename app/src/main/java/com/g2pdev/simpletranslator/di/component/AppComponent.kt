package com.g2pdev.simpletranslator.di.component

import com.g2pdev.simpletranslator.App
import com.g2pdev.simpletranslator.di.module.AppModule
import com.g2pdev.simpletranslator.di.module.FavoritesModule
import com.g2pdev.simpletranslator.di.module.TranslationMiscModule
import com.g2pdev.simpletranslator.di.module.TtsModule
import com.g2pdev.simpletranslator.interactor.favorite.*
import com.g2pdev.simpletranslator.interactor.translation.cache.GetTranslationLanguagePair
import com.g2pdev.simpletranslator.interactor.translation.cache.SaveTranslationLanguagePair
import com.g2pdev.simpletranslator.ui.mvp.favorite.FavoritesAdapter
import com.g2pdev.simpletranslator.ui.mvp.favorite.FavoritesPresenter
import com.g2pdev.simpletranslator.ui.mvp.language.TranslationModelsPresenter
import com.g2pdev.simpletranslator.ui.mvp.translate.TranslatePresenter
import com.g2pdev.simpletranslator.util.DbTestHelper
import com.g2pdev.simpletranslator.work.DownloadModelWorker
import com.g2pdev.translation.translation.di.FirebaseModule
import com.g2pdev.translation.translation.di.TranslationModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        FirebaseModule::class,
        TranslationModule::class,
        TranslationMiscModule::class,
        FavoritesModule::class,
        TtsModule::class
    ]
)
@Singleton
interface AppComponent {

    val getFavoriteTranslations: GetFavoriteTranslations
    val addFavoriteTranslation: AddFavoriteTranslation
    val deleteFavoriteTranslation: DeleteFavoriteTranslation
    val translationIsInFavorites: TranslationIsInFavorites
    val createFavoriteTranslation: CreateFavoriteTranslation
    val addOrRemoveFavoriteTranslation: AddOrRemoveFavoriteTranslation

    val getTranslationLanguagePair: GetTranslationLanguagePair
    val saveTranslationLanguagePair: SaveTranslationLanguagePair

    val dbTestHelper: DbTestHelper

    fun inject(app: App)

    fun inject(downloadModelWorker: DownloadModelWorker)

    fun inject(translatePresenter: TranslatePresenter)
    fun inject(translationModelsPresenter: TranslationModelsPresenter)
    fun inject(favoritesPresenter: FavoritesPresenter)
    fun inject(favoritesAdapter: FavoritesAdapter)

}