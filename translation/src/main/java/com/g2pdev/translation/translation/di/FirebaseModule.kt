package com.g2pdev.translation.translation.di

import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseNaturalLanguage(): FirebaseNaturalLanguage {
        return FirebaseNaturalLanguage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseModelManager(): FirebaseModelManager {
        return FirebaseModelManager.getInstance()
    }

}