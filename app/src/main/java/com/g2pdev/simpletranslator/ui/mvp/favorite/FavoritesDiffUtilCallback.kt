package com.g2pdev.simpletranslator.ui.mvp.favorite

import androidx.recyclerview.widget.DiffUtil
import com.g2pdev.simpletranslator.db.FavoriteTranslation

class FavoritesDiffUtilCallback(
    private val oldList: List<FavoriteTranslation>,
    private val newList: List<FavoriteTranslation>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}