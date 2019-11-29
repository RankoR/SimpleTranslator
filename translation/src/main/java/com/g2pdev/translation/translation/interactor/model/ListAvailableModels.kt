package com.g2pdev.translation.translation.interactor.model

import com.g2pdev.translation.translation.model.TranslationModel
import com.g2pdev.translation.translation.repository.TranslationModelsRepository
import io.reactivex.Single

interface ListAvailableModels {
    fun exec(): Single<Collection<TranslationModel>>
}

class ListAvailableModelsImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListAvailableModels {

    override fun exec() = translationModelsRepository.listAvailableModels()
}