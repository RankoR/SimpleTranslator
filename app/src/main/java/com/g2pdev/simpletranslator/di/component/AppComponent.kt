package com.g2pdev.simpletranslator.di.component

import com.g2pdev.simpletranslator.App
import com.g2pdev.simpletranslator.di.module.AppModule
import com.g2pdev.simpletranslator.di.module.FavoritesModule
import com.g2pdev.simpletranslator.di.module.FirebaseModule
import com.g2pdev.simpletranslator.di.module.TranslationModule
import com.g2pdev.simpletranslator.interactor.favorite.*
import com.g2pdev.simpletranslator.interactor.translation.cache.GetTranslationLanguagePair
import com.g2pdev.simpletranslator.interactor.translation.cache.SaveTranslationLanguagePair
import com.g2pdev.simpletranslator.ui.mvp.language.TranslationModelsPresenter
import com.g2pdev.simpletranslator.ui.mvp.translate.TranslatePresenter
import com.g2pdev.simpletranslator.util.DbTestHelper
import com.g2pdev.simpletranslator.work.DownloadModelWorker
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        FirebaseModule::class,
        TranslationModule::class,
        FavoritesModule::class
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

}