package com.g2pdev.simpletranslator.repository

import com.g2pdev.simpletranslator.translation.model.TranslationModel

data class TranslationModelWithState(
    val model: TranslationModel,
    val state: ModelState
)