package com.g2pdev.simpletranslator.interactor.tts

import com.g2pdev.simpletranslator.repository.tts.TextToSpeechRepository
import com.g2pdev.translation.translation.language.Language
import io.reactivex.Completable

interface CheckIfTtsLanguageAvailable {
    fun exec(language: Language): Completable
}

class CheckIfTtsLanguageAvailableImpl(
    private val textToSpeechRepository: TextToSpeechRepository
) : CheckIfTtsLanguageAvailable {

    override fun exec(language: Language): Completable {
        return textToSpeechRepository.checkLanguageAvailability(language.toLocale())
    }
}