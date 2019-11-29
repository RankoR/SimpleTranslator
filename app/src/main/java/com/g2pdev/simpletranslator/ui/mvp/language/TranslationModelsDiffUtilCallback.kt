package com.g2pdev.simpletranslator.ui.mvp.language

import androidx.recyclerview.widget.DiffUtil
import com.g2pdev.translation.translation.model.TranslationModelWithState

class TranslationModelsDiffUtilCallback(
    private val oldList: List<TranslationModelWithState>,
    private val newList: List<TranslationModelWithState>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].model.language == newList[newItemPosition].model.language
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}