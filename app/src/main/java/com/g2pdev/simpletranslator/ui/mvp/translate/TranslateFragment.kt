package com.g2pdev.simpletranslator.ui.mvp.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.translation.language.LanguagePair
import com.g2pdev.simpletranslator.ui.mvp.base.BaseMvpFragment
import com.g2pdev.simpletranslator.ui.mvp.language.download.ModelDownloadRequiredFragment
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

        sourceLanguageTv.setOnClickListener {
            presenter.onSourceLanguageChangeClick()
        }

        targetLanguageTv.setOnClickListener {
            presenter.onTargetLanguageChangeClick()
        }

        swapLanguagesBtn.setOnClickListener {
            presenter.onSwapLanguagesClick()
        }
    }

    override fun setTextToTranslate(text: String) {
        Timber.i("Text to translate: $text")

        sourceTv.setText(text)
    }

    override fun showLoading(loading: Boolean) {
        Timber.i("Loading: $loading")
    }

    override fun showTranslation(text: String) {
        Timber.i("Translation: $text")

        targetTv.text = text
    }

    override fun showError(e: Throwable) {
        Timber.e("Error: $e")
    }

    override fun disableInputs(disable: Boolean) {
        sourceTv.isEnabled = !disable
    }

    override fun disableLanguageChange(disable: Boolean) {
        sourceLanguageTv.isEnabled = !disable
        targetLanguageTv.isEnabled = !disable
        swapLanguagesBtn.isEnabled = !disable
    }

    override fun showLanguagePair(languagePair: LanguagePair) {
        Timber.i("Show language pair: $languagePair")

        sourceLanguageTv.text = languagePair.source.name
        targetLanguageTv.text = languagePair.source.name
    }

    override fun showSourceLanguageChooser() {
        // TODO
    }

    override fun showTargetLanguageChooser() {
        // TODO
    }

    override fun showModelRequired(languagePair: LanguagePair) {
        val fragment = ModelDownloadRequiredFragment.newInstance(languagePair)
        fragment.onDownloadClickListener = {
            val direction = TranslateFragmentDirections.actionTranslateFragmentToDownloadModelsFragment()
            findNavController().navigate(direction)
        }
        fragment.show(fragmentManager)
    }

    private companion object {
        private const val inputDebounceTime = 200L
        private const val minTextLength = 2
    }
}