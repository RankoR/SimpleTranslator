package com.g2pdev.simpletranslator.ui.mvp.language

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.g2pdev.simpletranslator.R
import com.g2pdev.translation.translation.model.ModelState
import com.g2pdev.translation.translation.model.TranslationModelWithState

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
        private val downloadBtn: View = itemView.findViewById(R.id.downloadBtn)
        private val deleteBtn: View = itemView.findViewById(R.id.deleteBtn)
        private val loadingView: View = itemView.findViewById(R.id.loadingPb)

        init {
            itemView.setOnClickListener {
                itemView.getTranslationModelFromTag()?.let { model ->
                    onModelClickListener?.invoke(model)
                }
            }

            downloadBtn.setOnClickListener {
                itemView.getTranslationModelFromTag()?.let { model ->
                    onModelActionButtonClickListener?.invoke(model)
                }
            }

            deleteBtn.setOnClickListener {
                itemView.getTranslationModelFromTag()?.let { model ->
                    onModelActionButtonClickListener?.invoke(model)
                }
            }
        }

        fun bind(translationModelWithState: TranslationModelWithState) {
            itemView.tag = translationModelWithState

            titleTv.text = translationModelWithState.model.name

            when (translationModelWithState.state) {
                ModelState.NOT_DOWNLOADED -> {
                    deleteBtn.isVisible = false
                    loadingView.isVisible = false
                    downloadBtn.isVisible = true
                }
                ModelState.DOWNLOADING -> {
                    deleteBtn.isVisible = false
                    downloadBtn.isVisible = false
                    loadingView.isVisible = true
                }
                ModelState.DOWNLOADED -> {
                    downloadBtn.isVisible = false
                    loadingView.isVisible = false
                    deleteBtn.isVisible = translationModelWithState.model.isDeletable
                }
            }
        }

        private fun View.getTranslationModelFromTag(): TranslationModelWithState? {
            return tag as? TranslationModelWithState
        }
    }


}