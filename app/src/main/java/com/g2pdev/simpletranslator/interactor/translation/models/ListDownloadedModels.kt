package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.model.TranslationModel
import io.reactivex.Single

interface ListDownloadedModels {
    fun exec(): Single<Collection<TranslationModel>>
}

class ListDownloadedModelsImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListDownloadedModels {

    override fun exec() = translationModelsRepository.listDownloadedModels()
}