package com.g2pdev.simpletranslator.di.component

import com.g2pdev.simpletranslator.di.module.AppModule
import com.g2pdev.simpletranslator.di.module.FirebaseModule
import com.g2pdev.simpletranslator.di.module.TranslationModule
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        FirebaseModule::class,
        TranslationModule::class
    ]
)
interface AppComponent {
}