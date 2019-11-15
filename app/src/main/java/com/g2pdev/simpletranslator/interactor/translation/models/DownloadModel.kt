package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.model.TranslationModel
import io.reactivex.Completable

interface DownloadModel {
    fun exec(model: TranslationModel): Completable
}

class DownloadModelImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : DownloadModel {

    override fun exec(model: TranslationModel) = translationModelsRepository.downloadModel(model)
}