package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.ModelState
import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import com.g2pdev.simpletranslator.translation.language.Language
import io.reactivex.Single
import io.reactivex.functions.Function3

interface ListModels {
    fun exec(): Single<Map<Language, ModelState>>

}

class ListModelsImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListModels {

    override fun exec(): Single<Map<Language, ModelState>> {
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

                        model to state
                    }.toMap()
            }
        )
    }
}