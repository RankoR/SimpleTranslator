package com.g2pdev.simpletranslator.repository.tts

import com.g2pdev.simpletranslator.interactor.tts.CheckIfTtsLanguageAvailable
import com.g2pdev.translation.translation.language.Language
import io.reactivex.Completable

interface SpeakText {
    fun exec(language: Language, text: String): Completable
}

class SpeakTextImpl(
    private val textToSpeechRepository: TextToSpeechRepository,
    private val checkIfTtsLanguageAvailable: CheckIfTtsLanguageAvailable
) : SpeakText {

    override fun exec(language: Language, text: String): Completable {
        return checkIfTtsLanguageAvailable
            .exec(language)
            .andThen(textToSpeechRepository.speak(language.toLocale(), text))
    }
}