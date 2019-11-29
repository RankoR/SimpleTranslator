package com.g2pdev.simpletranslator.interactor.translation.models

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.g2pdev.translation.translation.util.TranslationModelSerializer
import com.g2pdev.translation.translation.model.TranslationModel
import com.g2pdev.simpletranslator.work.DownloadModelWorker

interface EnqueueDownloadModel {
    fun exec(translationModel: TranslationModel)
}

class EnqueueDownloadModelImpl(
    private val translationModelSerializer: TranslationModelSerializer,
    private val workManager: WorkManager
) : EnqueueDownloadModel {

    override fun exec(translationModel: TranslationModel) {
        val inputData = workDataOf(DownloadModelWorker.keyTranslationModel to translationModelSerializer.serialize(translationModel))

        val work = OneTimeWorkRequestBuilder<DownloadModelWorker>()
            .setInputData(inputData)
            .build()
        workManager.enqueue(work)
    }
}