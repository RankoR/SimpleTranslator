package com.g2pdev.translation.translation.interactor.model

import com.g2pdev.translation.translation.model.TranslationModel
import com.g2pdev.translation.translation.repository.TranslationModelsRepository
import io.reactivex.Completable

interface DownloadModel {
    fun exec(model: TranslationModel): Completable
}

class DownloadModelImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : DownloadModel {

    override fun exec(model: TranslationModel) = translationModelsRepository.downloadModel(model)
}