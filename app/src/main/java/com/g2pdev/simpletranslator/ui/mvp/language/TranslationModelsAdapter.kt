package com.g2pdev.simpletranslator.ui.mvp.language

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.translation.model.TranslationModelWithState

class TranslationModelsAdapter : RecyclerView.Adapter<TranslationModelsAdapter.ViewHolder>() {

    private var models = emptyList<TranslationModelWithState>()

    var onModelClickListener: ((translationModel: TranslationModelWithState) -> Unit)? = null
    var onModelActionButtonClickListener: ((translationModel: TranslationModelWithState) -> Unit)? = null

    fun setModels(translationModels: List<TranslationModelWithState>) {
        val diffUtilCallback = TranslationModelsDiffUtilCallback(models, translationModels)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)

        this.models = translationModels

        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_item_model, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = models.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(models[position])
    }

    /**
     * View holder
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        private val downloadBtn: Button = itemView.findViewById(R.id.downloadBtn)

        init {
            itemView.setOnClickListener { view ->
                view.getTranslationModelFromTag()?.let { model ->
                    onModelClickListener?.invoke(model)
                }
            }

            downloadBtn.setOnClickListener { view ->
                view.getTranslationModelFromTag()?.let { model ->
                    onModelActionButtonClickListener?.invoke(model)
                }
            }
        }

        fun bind(translationModelWithState: TranslationModelWithState) {
            itemView.tag = translationModelWithState
            downloadBtn.tag = translationModelWithState

            titleTv.text = translationModelWithState.model.name
            downloadBtn.text = translationModelWithState.state.name
            downloadBtn.isVisible = !translationModelWithState.model.isDeletable
        }

        private fun View.getTranslationModelFromTag(): TranslationModelWithState? {
            return tag as? TranslationModelWithState
        }
    }


}