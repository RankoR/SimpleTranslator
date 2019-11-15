package com.g2pdev.simpletranslator.ui.mvp.download

import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.simpletranslator.interactor.translation.models.DeleteModel
import com.g2pdev.simpletranslator.interactor.translation.models.DownloadModel
import com.g2pdev.simpletranslator.interactor.translation.models.ListModels
import com.g2pdev.simpletranslator.interactor.translation.models.ListenModelDownloadingStateChanges
import com.g2pdev.simpletranslator.translation.model.ModelState
import com.g2pdev.simpletranslator.translation.model.TranslationModelWithState
import com.g2pdev.simpletranslator.ui.mvp.base.BasePresenter
import com.g2pdev.simpletranslator.util.schedulersIoToMain
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class DownloadModelsPresenter : BasePresenter<DownloadModelsView>() {

    @Inject
    lateinit var listModels: ListModels

    @Inject
    lateinit var downloadModel: DownloadModel

    @Inject
    lateinit var deleteModel: DeleteModel

    @Inject
    lateinit var listenModelDownloadingStateChanges: ListenModelDownloadingStateChanges

    init {
        DiHolder.appComponent.inject(this)

        listenModelDownloadingStateChanges()
        listModels()
    }

    private fun listenModelDownloadingStateChanges() {
        listenModelDownloadingStateChanges
            .exec()
            .schedulersIoToMain()
            .subscribe({
                listModels()
            }, Timber::e)
            .disposeOnPresenterDestroy()
    }

    private fun listModels() {
        listModels
            .exec()
            .schedulersIoToMain()
            .subscribe({ models ->
                Timber.i("Got models: $models")

                viewState.showModels(models)
            }, {
                Timber.e(it)

                viewState.showError(it)
            })
            .disposeOnPresenterDestroy()
    }

    fun onModelClick(translationModel: TranslationModelWithState) {
        Timber.i("Clicked model: $translationModel")

        when (translationModel.state) {
            ModelState.NOT_DOWNLOADED -> deleteModel(translationModel)
            ModelState.DOWNLOADED -> downloadModel(translationModel)
            ModelState.DOWNLOADING -> {
            }
        }
    }

    private fun downloadModel(translationModel: TranslationModelWithState) {
        downloadModel
            .exec(translationModel.model)
            .schedulersIoToMain()
            .subscribe({
                // It does not wait until downloading complete
            }, Timber::e)
            .disposeOnPresenterDestroy()
    }

    private fun deleteModel(translationModel: TranslationModelWithState) {
        deleteModel
            .exec(translationModel.model)
            .schedulersIoToMain()
            .subscribe({
                listModels()
            }, Timber::e)
            .disposeOnPresenterDestroy()
    }
}