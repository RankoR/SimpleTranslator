package com.g2pdev.translation.translation.interactor.model

import com.g2pdev.translation.translation.model.TranslationModel
import com.g2pdev.translation.translation.repository.TranslationModelsRepository
import io.reactivex.Single

interface ListDownloadedModels {
    fun exec(): Single<Collection<TranslationModel>>
}

class ListDownloadedModelsImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListDownloadedModels {

    override fun exec() = translationModelsRepository.listDownloadedModels()
}