package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.language.Language
import com.g2pdev.simpletranslator.translation.model.TranslationModel
import io.reactivex.Single

interface IsModelDownloaded {
    fun exec(model: TranslationModel): Single<Boolean>
    fun exec(language: Language): Single<Boolean>
}

class IsModelDownloadedImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : IsModelDownloaded {

    override fun exec(model: TranslationModel) = translationModelsRepository.isModelDownloaded(model)
    override fun exec(language: Language) = translationModelsRepository.isModelDownloaded(language)
}