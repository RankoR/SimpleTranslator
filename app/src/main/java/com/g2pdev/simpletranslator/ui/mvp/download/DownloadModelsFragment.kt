package com.g2pdev.simpletranslator.ui.mvp.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.repository.TranslationModelWithState
import com.g2pdev.simpletranslator.ui.mvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_download_models.*
import moxy.presenter.InjectPresenter
import timber.log.Timber

class DownloadModelsFragment : BaseMvpFragment(), DownloadModelsView {

    private val adapter = DownloadModelsAdapter()

    @InjectPresenter
    lateinit var presenter: DownloadModelsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_download_models, container, false)
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