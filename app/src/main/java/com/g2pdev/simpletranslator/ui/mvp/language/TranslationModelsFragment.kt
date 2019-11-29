package com.g2pdev.simpletranslator.ui.mvp.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.g2pdev.simpletranslator.R
import com.g2pdev.translation.translation.model.TranslationModel
import com.g2pdev.translation.translation.model.TranslationModelWithState
import com.g2pdev.simpletranslator.ui.mvp.base.BaseMvpBottomSheetFragment
import com.g2pdev.translator.base.extension.schedulersIoToMain
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.fragment_translation_models.*
import moxy.presenter.InjectPresenter
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TranslationModelsFragment : BaseMvpBottomSheetFragment(), TranslationModelsView {

    override val isFullscreen = true

    private val adapter = TranslationModelsAdapter()

    @InjectPresenter
    lateinit var presenter: TranslationModelsPresenter

    var onModelSelectedListener: ((model: TranslationModel) -> Unit)? = null
    var onCloseListener: (() -> Unit)? = null

    override fun getFragmentTag() = fragmentTag

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translation_models, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            presenter.screenType = it.getSerializable(argScreenType) as TranslationModelsPresenter.ScreenType
        }

        closeIv.setOnClickListener { dismiss() }

        initRecyclerView()
        initSearch()
    }

    private fun initRecyclerView() {
        adapter.onModelClickListener = presenter::onModelClick
        adapter.onModelActionButtonClickListener = presenter::onActionButtonClick

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        modelsRv.layoutManager = layoutManager
        modelsRv.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        modelsRv.adapter = adapter
    }

    private fun initSearch() {
        searchEt
            .textChanges()
            .debounce(200L, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .map { it.trim() }
            .map(presenter::filterModels)
            .schedulersIoToMain()
            .subscribe(::showModels, Timber::e)
            .disposeOnDestroy()
    }

    override fun showModels(translationModels: List<TranslationModelWithState>) {
        Timber.i("Show models: $translationModels")

        adapter.setModels(translationModels)
    }

    override fun notifyModelSelected(translationModelWithState: TranslationModelWithState) {
        onModelSelectedListener?.invoke(translationModelWithState.model)
    }

    override fun showError(t: Throwable) {
        Timber.e(t)
    }

    override fun showModelNotDownloadedSelectionError() {
        Toast.makeText(context, R.string.error_model_not_downloaded_selection_error, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        onCloseListener?.invoke()

        onCloseListener = null
        onModelSelectedListener = null

        super.onDestroyView()
    }

    override fun close() {
        dismiss()
    }

    companion object {
        private const val fragmentTag = "TranslationModelsFragment"
        private const val argScreenType = "screen_type"

        fun newInstance(screenType: TranslationModelsPresenter.ScreenType): TranslationModelsFragment {
            val fragment = TranslationModelsFragment()
            fragment.arguments = bundleOf(argScreenType to screenType)

            return fragment
        }
    }
}