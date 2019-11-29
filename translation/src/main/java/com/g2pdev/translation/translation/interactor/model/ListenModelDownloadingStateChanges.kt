package com.g2pdev.translation.translation.interactor.model

import com.g2pdev.translation.translation.repository.TranslationModelsRepository
import io.reactivex.Observable


interface ListenModelDownloadingStateChanges {
    fun exec(): Observable<Unit>
}

class ListenModelDownloadingStateChangesImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListenModelDownloadingStateChanges {

    override fun exec() = translationModelsRepository.getDownloadingStateChangedObservable()
}