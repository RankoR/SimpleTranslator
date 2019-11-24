package com.g2pdev.simpletranslator.ui.mvp.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.db.FavoriteTranslation

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var favorites = emptyList<FavoriteTranslation>()

    var onCopyClickListener: ((favoriteTranslation: FavoriteTranslation) -> Unit)? = null
    var onShareClickListener: ((favoriteTranslation: FavoriteTranslation) -> Unit)? = null

    fun setFavorites(favorites: List<FavoriteTranslation>) {
        val diffUtilCallback = FavoritesDiffUtilCallback(this.favorites, favorites)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)

        this.favorites = favorites

        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_item_favorite, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = favorites.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    /**
     * View holder
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sourceLanguageTv: TextView = itemView.findViewById(R.id.sourceLanguageTv)
        private val sourceTextTv: TextView = itemView.findViewById(R.id.sourceTextTv)
        private val targetLanguageTv: TextView = itemView.findViewById(R.id.targetLanguageTv)
        private val targetTextTv: TextView = itemView.findViewById(R.id.targetTextTv)

        private val copyBtn: Button = itemView.findViewById(R.id.copyBtn)
        private val shareBtn: Button = itemView.findViewById(R.id.shareBtn)

        init {
            copyBtn.setOnClickListener { view ->
                view.getFavoriteTranslationFromTag()?.let {
                    onCopyClickListener?.invoke(it)
                }
            }
            shareBtn.setOnClickListener { view ->
                view.getFavoriteTranslationFromTag()?.let {
                    onShareClickListener?.invoke(it)
                }
            }
        }

        fun bind(favoriteTranslation: FavoriteTranslation) {
            itemView.tag = favoriteTranslation

            // TODO
            sourceTextTv.text = favoriteTranslation.sourceText
            targetTextTv.text = favoriteTranslation.targetText
        }

        private fun View.getFavoriteTranslationFromTag(): FavoriteTranslation? {
            return tag as? FavoriteTranslation
        }
    }


}