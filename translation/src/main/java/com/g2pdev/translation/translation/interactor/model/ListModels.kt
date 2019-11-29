package com.g2pdev.translation.translation.interactor.model

import com.g2pdev.translation.translation.model.ModelState
import com.g2pdev.translation.translation.model.TranslationModelWithState
import com.g2pdev.translation.translation.repository.TranslationModelsRepository
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