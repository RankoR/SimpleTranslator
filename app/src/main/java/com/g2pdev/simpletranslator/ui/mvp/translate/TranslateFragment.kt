package com.g2pdev.simpletranslator.ui.mvp.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.ui.mvp.base.BaseMvpFragment
import com.g2pdev.simpletranslator.util.schedulersIoToMain
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.fragment_translate.*
import moxy.presenter.InjectPresenter
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TranslateFragment : BaseMvpFragment(), TranslateView {

    @InjectPresenter
    lateinit var presenter: TranslatePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sourceTv
            .textChanges()
            .debounce(inputDebounceTime, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .distinctUntilChanged()
            .filter { it.length >= minTextLength }
            .schedulersIoToMain()
            .subscribe({ text ->
                presenter.translate(text)
            }, Timber::e)
            .disposeOnDestroy()

        downloadModelsBtn.setOnClickListener {
            val direction = TranslateFragmentDirections.actionTranslateFragmentToDownloadModelsFragment()
            findNavController().navigate(direction)
        }
    }

    override fun showLoading(loading: Boolean) {
        Timber.i("Loading: $loading")
    }

    override fun showTranslation(text: String) {
        Timber.i("Translation: $text")

        targetTv.setText(text)
    }

    override fun showError(e: Throwable) {
        Timber.e("Error: $e")
    }

    private companion object {
        private const val inputDebounceTime = 300L
        private const val minTextLength = 2
    }
}