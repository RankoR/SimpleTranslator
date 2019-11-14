package com.g2pdev.simpletranslator.interactor.translation.models

import com.g2pdev.simpletranslator.repository.TranslationModelsRepository
import io.reactivex.Observable


interface ListenModelDownloadingStateChanges {
    fun exec(): Observable<Unit>
}

class ListenModelDownloadingStateChangesImpl(
    private val translationModelsRepository: TranslationModelsRepository
) : ListenModelDownloadingStateChanges {

    override fun exec() = translationModelsRepository.getDownloadingStateChangedObservable()
}