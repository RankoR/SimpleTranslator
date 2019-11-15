package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.model.TranslationModel
import io.reactivex.Completable

interface DeleteModel {
    fun exec(translationModel: TranslationModel): Completable
}

class DeleteModelImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : DeleteModel {

    override fun exec(translationModel: TranslationModel) = translationModelsRepository.deleteModel(translationModel)
}