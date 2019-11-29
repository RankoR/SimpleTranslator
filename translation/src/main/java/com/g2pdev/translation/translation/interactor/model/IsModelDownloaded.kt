package com.g2pdev.translation.translation.interactor.model

import com.g2pdev.translation.translation.language.Language
import com.g2pdev.translation.translation.model.TranslationModel
import com.g2pdev.translation.translation.repository.TranslationModelsRepository
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