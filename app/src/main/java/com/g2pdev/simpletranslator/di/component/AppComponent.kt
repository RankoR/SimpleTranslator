package com.g2pdev.simpletranslator.di.component

import com.g2pdev.simpletranslator.di.module.FirebaseModule
import com.g2pdev.simpletranslator.di.module.TranslationModule
import dagger.Component

@Component(
    modules = [
        FirebaseModule::class,
        TranslationModule::class
    ]
)
interface AppComponent {
}