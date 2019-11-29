package com.g2pdev.translation.translation.interactor.model

import com.g2pdev.translation.translation.model.TranslationModel
import com.g2pdev.translation.translation.repository.TranslationModelsRepository
import io.reactivex.Completable

interface DeleteModel {
    fun exec(translationModel: TranslationModel): Completable
}

class DeleteModelImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : DeleteModel {

    override fun exec(translationModel: TranslationModel) = translationModelsRepository.deleteModel(translationModel)
}