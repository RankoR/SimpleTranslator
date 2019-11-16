package com.g2pdev.simpletranslator.translation.model

enum class ModelState(
    val value: Int
) {
    NOT_DOWNLOADED(2),
    DOWNLOADING(0),
    DOWNLOADED(0)
}