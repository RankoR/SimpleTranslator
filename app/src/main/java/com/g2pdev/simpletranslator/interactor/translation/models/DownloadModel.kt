package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.language.Language
import io.reactivex.Completable

interface DownloadModel {
    fun exec(language: Language): Completable
}

class DownloadModelImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : DownloadModel {

    override fun exec(language: Language) = translationModelsRepository.downloadModel(language)
}