package com.g2pdev.simpletranslator.di.component

import com.g2pdev.simpletranslator.App
import com.g2pdev.simpletranslator.di.module.AppModule
import com.g2pdev.simpletranslator.di.module.FirebaseModule
import com.g2pdev.simpletranslator.di.module.TranslationModule
import com.g2pdev.simpletranslator.ui.mvp.language.TranslationModelsPresenter
import com.g2pdev.simpletranslator.ui.mvp.translate.TranslatePresenter
import com.g2pdev.simpletranslator.work.DownloadModelWorker
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        FirebaseModule::class,
        TranslationModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(app: App)

    fun inject(downloadModelWorker: DownloadModelWorker)

    fun inject(translatePresenter: TranslatePresenter)
    fun inject(translationModelsPresenter: TranslationModelsPresenter)


}