package com.g2pdev.translation.translation.model

data class TranslationModelWithState(
    val model: TranslationModel,
    val state: ModelState
) : Comparable<TranslationModelWithState> {

    override fun compareTo(other: TranslationModelWithState): Int {
        return if (state != other.state) {
            state.value - other.state.value
        } else {
            model.compareTo(other.model)
        }
    }

}