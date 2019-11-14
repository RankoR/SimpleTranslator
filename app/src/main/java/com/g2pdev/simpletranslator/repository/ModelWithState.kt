package com.g2pdev.simpletranslator.repository

import com.g2pdev.simpletranslator.translation.language.Language

data class ModelWithState(
    val model: Language,
    val state: ModelState
)