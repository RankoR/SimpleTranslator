package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.translation.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.model.ModelState
import com.g2pdev.simpletranslator.translation.model.TranslationModelWithState
import io.reactivex.Single
import io.reactivex.functions.Function3

interface ListModels {
    fun exec(): Single<List<TranslationModelWithState>>

}

class ListModelsImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListModels {

    override fun exec(): Single<List<TranslationModelWithState>> {
        return Single.zip(
            translationModelsRepository.listAvailableModels(),
            translationModelsRepository.listDownloadedModels(),
            translationModelsRepository.getDownloadingModels(),

            Function3 { availableModels, downloadedModels, downloadingModels ->
                availableModels
                    .map { model ->
                        val state = when {
                            downloadedModels.contains(model) -> ModelState.DOWNLOADED
                            downloadingModels.contains(model) -> ModelState.DOWNLOADING
                            else -> ModelState.NOT_DOWNLOADED
                        }

                        TranslationModelWithState(model, state)
                    }
                    .sorted()
            }
        )
    }
}