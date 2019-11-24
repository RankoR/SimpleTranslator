package com.g2pdev.simpletranslator.repository.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import io.reactivex.Completable
import java.util.*
import java.util.concurrent.TimeUnit

interface TextToSpeechRepository {
    fun checkLanguageAvailability(locale: Locale): Completable
    fun speak(locale: Locale, text: String): Completable

    class NotAvailableException : Exception()
    class LanguageMissingDataException : Exception()
    class LanguageNotAvailableException : Exception()
}

class DefaultTextToSpeechRepositoryImpl(
    private val context: Context
) : TextToSpeechRepository {

    private var initializationState = InitializationState.NOT_INITIALIZED
    private var textToSpeech: TextToSpeech? = null

    /**
     * Note: this method is NOT thread safe but should work fine
     */
    private fun initialize(): Completable {
        when (initializationState) {
            InitializationState.INITIALIZING -> {
                return Completable
                    .timer(defaultInitializationTimeout, TimeUnit.MILLISECONDS)
                    .andThen(initialize())
            }

            InitializationState.INITIALIZED -> {
                return Completable.complete()
            }

            InitializationState.FAILED -> {
                return Completable.error(TextToSpeechRepository.NotAvailableException())
            }
            else -> {
            }
        }

        initializationState = InitializationState.INITIALIZING

        return Completable.create { emitter ->
            textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
                when (status) {
                    TextToSpeech.SUCCESS -> {
                        initializationState = InitializationState.INITIALIZED
                        emitter.onComplete()
                    }
                    else -> {
                        initializationState = InitializationState.FAILED
                        emitter.onError(TextToSpeechRepository.NotAvailableException())
                    }
                }
            })
        }
    }

    override fun checkLanguageAvailability(locale: Locale): Completable {
        return initialize()
            .andThen(Completable.fromCallable {
                requireNotNull(textToSpeech)

                when (textToSpeech?.isLanguageAvailable(locale)) {
                    TextToSpeech.LANG_AVAILABLE -> {
                    }
                    TextToSpeech.LANG_MISSING_DATA -> throw TextToSpeechRepository.LanguageMissingDataException()
                    else -> throw TextToSpeechRepository.LanguageNotAvailableException()
                }
            })
    }

    private fun waitForReadyToSpeak(): Completable {
        return Completable
            .fromCallable {
                requireNotNull(textToSpeech)

                while (textToSpeech?.isSpeaking != false) {
                    Thread.sleep(defaultWaitForReadyToSpeakSleep)
                }
            }
            .timeout(defaultWaitForReadyToSpeakTimeout, TimeUnit.MILLISECONDS)
    }

    override fun speak(locale: Locale, text: String): Completable {
        return waitForReadyToSpeak()
            .andThen(Completable.fromCallable {
                requireNotNull(textToSpeech)

                textToSpeech?.apply {
                    language = locale
                    speak(text, TextToSpeech.QUEUE_FLUSH, null, System.currentTimeMillis().toString())
                }

                while (textToSpeech?.isSpeaking != false) {
                    Thread.sleep(defaultWaitForReadyToSpeakSleep)
                }
            })
            .timeout(defaultSpeakTimeout, TimeUnit.MILLISECONDS)
    }

    /**
     * Initialization state
     */
    private enum class InitializationState {
        NOT_INITIALIZED,
        INITIALIZING,
        INITIALIZED,
        FAILED
    }

    private companion object {
        private const val defaultInitializationTimeout = 500L
        private const val defaultWaitForReadyToSpeakSleep = 300L
        private const val defaultWaitForReadyToSpeakTimeout = 30000L
        private const val defaultSpeakTimeout = 60000L
    }

}