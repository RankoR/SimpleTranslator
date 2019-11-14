package com.g2pdev.simpletranslator.ui.mvp.download

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.repository.ModelWithState

class DownloadModelsAdapter : RecyclerView.Adapter<DownloadModelsAdapter.ViewHolder>() {

    private var models = emptyList<ModelWithState>()

    var onModelClickListener: ((model: ModelWithState) -> Unit)? = null

    fun setModels(models: List<ModelWithState>) {
        // TODO: diff
        this.models = models
        this.notifyDataSetChanged()
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
                (view.tag as? ModelWithState)?.let { model ->
                    onModelClickListener?.invoke(model)
                }
            }
        }

        fun bind(modelWithState: ModelWithState) {
            itemView.tag = modelWithState

            titleTv.text = modelWithState.model.name
            downloadBtn.text = modelWithState.state.name
        }
    }


}