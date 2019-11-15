package com.g2pdev.simpletranslator.translation.model

enum class ModelState(
    val value: Int
) {
    NOT_DOWNLOADED(0),
    DOWNLOADING(1),
    DOWNLOADED(2)
}