package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.model.TranslationModel
import io.reactivex.Single

interface ListAvailableModels {
    fun exec(): Single<Collection<TranslationModel>>
}

class ListAvailableModelsImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListAvailableModels {

    override fun exec() = translationModelsRepository.listAvailableModels()
}