package com.g2pdev.simpletranslator.ui.mvp.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.repository.ModelState
import com.g2pdev.simpletranslator.translation.language.Language
import com.g2pdev.simpletranslator.ui.mvp.base.BaseMvpFragment
import moxy.presenter.InjectPresenter
import timber.log.Timber

class DownloadModelsFragment : BaseMvpFragment(), DownloadModelsView {

    @InjectPresenter
    lateinit var presenter: DownloadModelsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_download_models, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showModels(languages: Map<Language, ModelState>) {
        Timber.i("Show models: $languages")
    }

    override fun showError(t: Throwable) {
        Timber.e(t)
    }
}