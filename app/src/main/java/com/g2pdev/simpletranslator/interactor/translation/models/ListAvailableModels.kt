package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.language.Language
import io.reactivex.Single

interface ListAvailableModels {
    fun exec(): Single<Collection<Language>>
}

class ListAvailableModelsImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListAvailableModels {

    override fun exec() = translationModelsRepository.listAvailableModels()
}