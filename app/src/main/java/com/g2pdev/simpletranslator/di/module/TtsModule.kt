package com.g2pdev.simpletranslator.di.module

import android.content.Context
import com.g2pdev.simpletranslator.interactor.tts.CheckIfTtsLanguageAvailable
import com.g2pdev.simpletranslator.interactor.tts.CheckIfTtsLanguageAvailableImpl
import com.g2pdev.simpletranslator.repository.tts.DefaultTextToSpeechRepositoryImpl
import com.g2pdev.simpletranslator.repository.tts.SpeakText
import com.g2pdev.simpletranslator.repository.tts.SpeakTextImpl
import com.g2pdev.simpletranslator.repository.tts.TextToSpeechRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TtsModule {

    @Provides
    @Singleton
    fun provideTextToSpeechRepository(
        context: Context
    ): TextToSpeechRepository = DefaultTextToSpeechRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideCheckIfTtsLanguageAvailable(
        textToSpeechRepository: TextToSpeechRepository
    ): CheckIfTtsLanguageAvailable = CheckIfTtsLanguageAvailableImpl(textToSpeechRepository)

    @Provides
    @Singleton
    fun provideSpeakText(
        textToSpeechRepository: TextToSpeechRepository,
        checkIfTtsLanguageAvailable: CheckIfTtsLanguageAvailable
    ): SpeakText = SpeakTextImpl(textToSpeechRepository, checkIfTtsLanguageAvailable)
}