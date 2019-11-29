package com.g2pdev.simpletranslator.di.module

import android.content.Context
import androidx.work.WorkManager
import com.g2pdev.simpletranslator.cache.TextToTranslateCache
import com.g2pdev.simpletranslator.cache.TextToTranslateCacheImpl
import com.g2pdev.simpletranslator.cache.TranslationModelsCache
import com.g2pdev.simpletranslator.cache.TranslationModelsCacheImpl
import com.g2pdev.simpletranslator.interactor.translation.cache.*
import com.g2pdev.simpletranslator.interactor.translation.models.EnqueueDownloadModel
import com.g2pdev.simpletranslator.interactor.translation.models.EnqueueDownloadModelImpl
import com.g2pdev.translation.translation.util.LanguageNameProvider
import com.g2pdev.translation.translation.util.TranslationModelSerializer
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TranslationMiscModule {

    @Provides
    @Singleton
    fun provideEnqueueDownloadModel(
        translationModelSerializer: TranslationModelSerializer,
        workManager: WorkManager
    ): EnqueueDownloadModel = EnqueueDownloadModelImpl(translationModelSerializer, workManager)

    @Provides
    @Singleton
    fun provideTextToTranslateCache(
        gson: Gson,
        context: Context
    ): TextToTranslateCache = TextToTranslateCacheImpl(gson, context)

    @Provides
    @Singleton
    fun provideGetLastTextToTranslate(
        textToTranslateCache: TextToTranslateCache
    ): GetLastTextToTranslate = GetLastTextToTranslateImpl(textToTranslateCache)

    @Provides
    @Singleton
    fun provideSaveLastTextToTranslate(
        textToTranslateCache: TextToTranslateCache
    ): SaveLastTextToTranslate = SaveLastTextToTranslateImpl(textToTranslateCache)

    @Provides
    @Singleton
    fun provideTranslationModelsCache(
        gson: Gson,
        context: Context
    ): TranslationModelsCache = TranslationModelsCacheImpl(gson, context)

    @Provides
    @Singleton
    fun provideGetTranslationLanguagePair(
        translationModelsCache: TranslationModelsCache,
        languageNameProvider: LanguageNameProvider
    ): GetTranslationLanguagePair = GetTranslationLanguagePairImpl(translationModelsCache, languageNameProvider)

    @Provides
    @Singleton
    fun provideSaveTranslationLanguagePair(
        translationModelsCache: TranslationModelsCache
    ): SaveTranslationLanguagePair = SaveTranslationLanguagePairImpl(translationModelsCache)
}