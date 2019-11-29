package com.g2pdev.simpletranslator.ui.mvp.language

import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.translation.translation.interactor.model.DeleteModel
import com.g2pdev.simpletranslator.interactor.translation.models.EnqueueDownloadModel
import com.g2pdev.translation.translation.interactor.model.ListModels
import com.g2pdev.translation.translation.interactor.model.ListenModelDownloadingStateChanges
import com.g2pdev.translation.translation.model.ModelState
import com.g2pdev.translation.translation.model.TranslationModelWithState
import com.g2pdev.simpletranslator.ui.mvp.base.BasePresenter
import com.g2pdev.translator.base.extension.schedulersIoToMain
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class TranslationModelsPresenter : BasePresenter<TranslationModelsView>() {

    @Inject
    lateinit var listModels: ListModels

    @Inject
    lateinit var enqueueDownloadModel: EnqueueDownloadModel

    @Inject
    lateinit var deleteModel: DeleteModel

    @Inject
    lateinit var listenModelDownloadingStateChanges: ListenModelDownloadingStateChanges

    var screenType = ScreenType.DOWNLOADER

    private var models: List<TranslationModelWithState>? = null

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
                Timber.i("Model downloading state changed")

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

                this.models = models
                viewState.showModels(models)
            }, {
                Timber.e(it)

                viewState.showError(it)
            })
            .disposeOnPresenterDestroy()
    }

    fun filterModels(query: String): List<TranslationModelWithState> {
        if (query.isBlank()) {
            return models ?: emptyList()
        }

        return models
            ?.filter { it.model.name.contains(query, true) }
            ?: emptyList()
    }

    fun onModelClick(translationModel: TranslationModelWithState) {
        Timber.i("Clicked model: $translationModel")

        if (screenType == ScreenType.DOWNLOADER) {
            performModelActions(translationModel)
        } else {
            if (translationModel.state == ModelState.DOWNLOADED) {
                viewState.notifyModelSelected(translationModel)
                viewState.close()
            } else {
                viewState.showModelNotDownloadedSelectionError()
            }
        }
    }

    fun onActionButtonClick(translationModel: TranslationModelWithState) {
        Timber.i("Clicked action button for model: $translationModel")

        performModelActions(translationModel)
    }

    private fun performModelActions(translationModel: TranslationModelWithState) {
        when (translationModel.state) {
            ModelState.NOT_DOWNLOADED -> downloadModel(translationModel)
            ModelState.DOWNLOADED -> deleteModel(translationModel)
            ModelState.DOWNLOADING -> {
            }
        }
    }

    private fun downloadModel(translationModel: TranslationModelWithState) {
        Timber.i("Will download model: $translationModel")

        enqueueDownloadModel.exec(translationModel.model)
    }

    private fun deleteModel(translationModel: TranslationModelWithState) {
        deleteModel
            .exec(translationModel.model)
            .schedulersIoToMain()
            .subscribe(::listModels, Timber::e)
            .disposeOnPresenterDestroy()
    }

    /**
     * Type
     */
    enum class ScreenType {
        DOWNLOADER,
        CHOOSER
    }
}