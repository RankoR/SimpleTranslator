package com.g2pdev.simpletranslator.di.module

import android.content.res.Resources
import com.g2pdev.simpletranslator.interactor.translation.Translate
import com.g2pdev.simpletranslator.interactor.translation.TranslateImpl
import com.g2pdev.simpletranslator.interactor.translation.models.*
import com.g2pdev.simpletranslator.repository.FirebaseTranslationModelsRepository
import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.FirebaseTranslatorProvider
import com.g2pdev.simpletranslator.translation.FirebaseTranslatorProviderImpl
import com.g2pdev.simpletranslator.translation.language.FirebaseLanguageConverter
import com.g2pdev.simpletranslator.translation.language.LanguageNameProvider
import com.g2pdev.simpletranslator.translation.language.LanguageNameProviderImpl
import com.g2pdev.simpletranslator.translation.translator.FirebaseMLTranslator
import com.g2pdev.simpletranslator.translation.translator.Translator
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TranslationModule {

    @Provides
    @Singleton
    fun provideLanguageConverter(): FirebaseLanguageConverter = FirebaseLanguageConverter()

    @Provides
    @Singleton
    fun provideLanguageNameProvider(
        resources: Resources
    ): LanguageNameProvider = LanguageNameProviderImpl(resources)

    @Provides
    @Singleton
    fun provideTranslationModelsRepository(
        firebaseModelManager: FirebaseModelManager,
        firebaseLanguageConverter: FirebaseLanguageConverter,
        languageNameProvider: LanguageNameProvider
    ): TranslationModelsRepository = FirebaseTranslationModelsRepository(firebaseModelManager, firebaseLanguageConverter, languageNameProvider)

    @Provides
    @Singleton
    fun provideFirebaseTranslatorProvider(
        firebaseNaturalLanguage: FirebaseNaturalLanguage,
        firebaseLanguageConverter: FirebaseLanguageConverter
    ): FirebaseTranslatorProvider = FirebaseTranslatorProviderImpl(firebaseNaturalLanguage, firebaseLanguageConverter)

    @Provides
    @Singleton
    fun provideTranslator(
        firebaseTranslatorProvider: FirebaseTranslatorProvider
    ): Translator = FirebaseMLTranslator(firebaseTranslatorProvider)

    @Provides
    @Singleton
    fun provideIsModelDownloaded(
        translationModelsRepository: TranslationModelsRepository
    ): IsModelDownloaded = IsModelDownloadedImpl(translationModelsRepository)

    @Provides
    @Singleton
    fun provideListAvailableModels(
        translationModelsRepository: TranslationModelsRepository
    ): ListAvailableModels = ListAvailableModelsImpl(translationModelsRepository)

    @Provides
    @Singleton
    fun provideListDownloadedModels(
        translationModelsRepository: TranslationModelsRepository
    ): ListDownloadedModels = ListDownloadedModelsImpl(translationModelsRepository)

    @Provides
    @Singleton
    fun provideDownloadModel(
        translationModelsRepository: TranslationModelsRepository
    ): DownloadModel =
        DownloadModelImpl(translationModelsRepository)

    @Provides
    @Singleton
    fun provideTranslate(
        translator: Translator,
        isModelDownloaded: IsModelDownloaded
    ): Translate = TranslateImpl(translator, isModelDownloaded)

    @Provides
    @Singleton
    fun provideListModels(
        translationModelsRepository: TranslationModelsRepository
    ): ListModels = ListModelsImpl(translationModelsRepository)

    @Provides
    @Singleton
    fun provideListenModelDownloadingStateChanges(
        translationModelsRepository: TranslationModelsRepository
    ): ListenModelDownloadingStateChanges = ListenModelDownloadingStateChangesImpl(translationModelsRepository)

    @Provides
    @Singleton
    fun provideDeleteModel(
        translationModelsRepository: TranslationModelsRepository
    ): DeleteModel = DeleteModelImpl(translationModelsRepository)
}