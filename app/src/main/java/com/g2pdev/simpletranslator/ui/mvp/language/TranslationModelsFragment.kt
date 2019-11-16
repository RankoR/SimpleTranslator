package com.g2pdev.simpletranslator.ui.mvp.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.translation.model.TranslationModelWithState
import com.g2pdev.simpletranslator.ui.mvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_translation_models.*
import moxy.presenter.InjectPresenter
import timber.log.Timber

class TranslationModelsFragment : BaseMvpFragment(), TranslationModelsView {

    private val adapter = TranslationModelsAdapter()

    @InjectPresenter
    lateinit var presenter: TranslationModelsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translation_models, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter.onModelClickListener = { model ->
            presenter.onModelClick(model)
        }

        modelsRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        modelsRv.adapter = adapter
    }

    override fun showModels(translationModels: List<TranslationModelWithState>) {
        Timber.i("Show models: $translationModels")

        adapter.setModels(translationModels)
    }

    override fun showError(t: Throwable) {
        Timber.e(t)
    }
}