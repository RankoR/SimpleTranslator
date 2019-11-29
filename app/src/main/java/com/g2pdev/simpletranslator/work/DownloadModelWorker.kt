package com.g2pdev.simpletranslator.work

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.g2pdev.simpletranslator.di.DiHolder
import com.g2pdev.translation.translation.interactor.model.DownloadModel
import com.g2pdev.translation.translation.util.TranslationModelSerializer
import com.g2pdev.translation.translation.model.TranslationModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class DownloadModelWorker(context: Context, workerParams: WorkerParameters) : RxWorker(context, workerParams) {

    @Inject
    lateinit var translationModelSerializer: TranslationModelSerializer

    @Inject
    lateinit var downloadModel: DownloadModel

    init {
        DiHolder.appComponent.inject(this)
    }

    override fun createWork(): Single<Result> {
        Timber.i("Work started")

        return downloadModel
            .exec(getTranslationModel())
            .toSingleDefault(Result.success())
            .onErrorResumeNext {
                Timber.e(it)

                Single.just(Result.retry())
            }
    }

    override fun getBackgroundScheduler(): Scheduler = AndroidSchedulers.mainThread()

    private fun getTranslationModel(): TranslationModel {
        inputData.getString(keyTranslationModel)?.let {
            return translationModelSerializer.deserialize(it)
        } ?: throw IllegalArgumentException("Failed to get translation model: string is null")
    }

    companion object {
        const val keyTranslationModel = "translationModel"
    }
}