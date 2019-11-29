package com.g2pdev.translation.translation.model

enum class ModelState(
    val value: Int
) {
    NOT_DOWNLOADED(2),
    DOWNLOADING(2),
    DOWNLOADED(0)
}