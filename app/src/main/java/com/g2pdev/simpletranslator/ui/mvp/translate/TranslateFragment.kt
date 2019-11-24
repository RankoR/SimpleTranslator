package com.g2pdev.simpletranslator.ui.mvp.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.translation.language.LanguagePair
import com.g2pdev.simpletranslator.ui.mvp.base.BaseMvpFragment
import com.g2pdev.simpletranslator.ui.mvp.language.TranslationModelsFragment
import com.g2pdev.simpletranslator.ui.mvp.language.TranslationModelsPresenter
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
            .map { it.trim() }
            .filter { it.length >= minTextLength }
            .schedulersIoToMain()
            .subscribe({ text ->
                presenter.translate(text)
            }, Timber::e)
            .disposeOnDestroy()

        downloadModelsBtn.setOnClickListener {
            val translationModelsFragment = TranslationModelsFragment.newInstance(TranslationModelsPresenter.ScreenType.DOWNLOADER)
            translationModelsFragment.show(fragmentManager)
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

        addToFavoritesBtn.setOnClickListener {
            presenter.addToFavorites(
                sourceText = sourceTv.text.toString(),
                targetText = targetTv.text.toString()
            )
        }

        copyBtn.setOnClickListener {
            presenter.copyToClipboard(targetTv.text.trim().toString())
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
        targetLanguageTv.text = languagePair.target.name
    }

    override fun showSourceLanguageChooser() {
        val translationModelsFragment = TranslationModelsFragment.newInstance(TranslationModelsPresenter.ScreenType.CHOOSER)
        translationModelsFragment.onModelSelectedListener = presenter::onSourceLanguageChanged

        translationModelsFragment.show(fragmentManager)
    }

    override fun showTargetLanguageChooser() {
        val translationModelsFragment = TranslationModelsFragment.newInstance(TranslationModelsPresenter.ScreenType.CHOOSER)
        translationModelsFragment.onModelSelectedListener = presenter::onTargetLanguageChanged

        translationModelsFragment.show(fragmentManager)
    }

    override fun showModelRequired(languagePair: LanguagePair) {
        val fragment = ModelDownloadRequiredFragment.newInstance(languagePair)
        fragment.onDownloadClickListener = {
            val translationFragment = TranslationModelsFragment.newInstance(TranslationModelsPresenter.ScreenType.DOWNLOADER)
            translationFragment.onCloseListener = {
                presenter.loadTranslationLanguagesAndReTranslate()
            }
            translationFragment.show(fragmentManager)
        }
        fragment.show(fragmentManager)
    }

    override fun enableAddToFavorites(enable: Boolean) {
        addToFavoritesBtn.isEnabled = enable
    }

    override fun showTranslationIsInFavorites(isInFavorites: Boolean) {
        addToFavoritesBtn.text = if (isInFavorites) "Fav -" else "Fav +"
    }

    override fun showCopiedToClipboard() {
        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_LONG).show()
    }

    private companion object {
        private const val inputDebounceTime = 200L
        private const val minTextLength = 2
    }
}