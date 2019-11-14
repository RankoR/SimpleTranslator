package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.ModelState
import com.g2pdev.simpletranslator.repository.ModelWithState
import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import io.reactivex.Single
import io.reactivex.functions.Function3

interface ListModels {
    fun exec(): Single<List<ModelWithState>>

}

class ListModelsImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListModels {

    override fun exec(): Single<List<ModelWithState>> {
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

                        ModelWithState(model, state)
                    }
            }
        )
    }
}