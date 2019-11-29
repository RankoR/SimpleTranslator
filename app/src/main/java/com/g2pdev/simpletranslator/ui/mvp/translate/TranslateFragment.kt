package com.g2pdev.simpletranslator.ui.mvp.translate

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.g2pdev.simpletranslator.R
import com.g2pdev.translation.translation.language.LanguagePair
import com.g2pdev.simpletranslator.ui.mvp.base.BaseMvpFragment
import com.g2pdev.simpletranslator.ui.mvp.language.TranslationModelsFragment
import com.g2pdev.simpletranslator.ui.mvp.language.TranslationModelsPresenter
import com.g2pdev.simpletranslator.ui.mvp.language.download.ModelDownloadRequiredFragment
import com.g2pdev.translator.base.extension.schedulersIoToMain
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_translate.*
import moxy.presenter.InjectPresenter
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TranslateFragment : BaseMvpFragment(
    lightStatusBar = false,
    lightNavigationBar = false
), TranslateView {

    @InjectPresenter
    lateinit var presenter: TranslatePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sourceTv
            .textChanges()
            .map { it.toString() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { clearSourceTextBtn.isVisible = it.isNotEmpty() }
            .observeOn(Schedulers.io())
            .debounce(inputDebounceTime, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .schedulersIoToMain()
            .subscribe({ text ->
                presenter.translate(text)
            }, Timber::e)
            .disposeOnDestroy()

        favoritesBtn.setOnClickListener {
            findNavController().navigate(TranslateFragmentDirections.translateFragmentToFavoritesFragment())
        }

        settingsBtn.setOnClickListener {
            presenter.openDownloadLanguagesClick()
        }

        clearSourceTextBtn.setOnClickListener {
            presenter.clearSourceText()
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
            presenter.copyToClipboard(getTargetText())
        }

        shareBtn.setOnClickListener {
            presenter.share(getTargetText())
        }

        ttsBtn.setOnClickListener {
            presenter.speakText(getTargetText())
        }
    }

    private fun getTargetText(): String = targetTv.text.trim().toString()

    override fun setTextToTranslate(text: String) {
        sourceTv.setText(text)
    }

    override fun showLoading(loading: Boolean) {
    }

    override fun showTranslation(text: String) {
        targetTv.text = text
    }

    override fun showError(e: Throwable) {
        Timber.e("Error: $e")
    }

    override fun disableInputs(disable: Boolean) {
        sourceTv.isEnabled = !disable
    }

    override fun disableLanguageChange(disable: Boolean) {
        sourceLanguageTv.isClickable = !disable
        targetLanguageTv.isClickable = !disable
        swapLanguagesBtn.isClickable = !disable
    }

    override fun showLanguagePair(languagePair: LanguagePair) {
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
        fragment.onDownloadClickListener = presenter::openDownloadLanguagesClick
        fragment.show(fragmentManager)
    }

    override fun openDownloadModelsScreen() {
        val translationFragment = TranslationModelsFragment.newInstance(TranslationModelsPresenter.ScreenType.DOWNLOADER)
        translationFragment.onCloseListener = {
            presenter.loadTranslationLanguagesAndReTranslate()
        }
        translationFragment.show(fragmentManager)
    }

    override fun enableAddToFavorites(enable: Boolean) {
        addToFavoritesBtn.isClickable = enable
    }

    override fun showTranslationIsInFavorites(isInFavorites: Boolean) {
        val imageResId = if (isInFavorites) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_not_filled
        addToFavoritesBtn.setImageResource(imageResId)
    }

    override fun showCopiedToClipboard() {
        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_LONG).show()
    }

    override fun shareText(text: String) {
        Intent(Intent.ACTION_SEND)
            .apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            .also {
                startActivity(it)
            }
    }

    override fun enableTts(enable: Boolean) {
        ttsBtn.isEnabled = enable
    }

    override fun showTtsSpeaking(show: Boolean) {
        // TODO in future
    }

    override fun clearSourceText() {
        sourceTv.setText("")
    }

    override fun showTargetActions(show: Boolean) {
        actionsLayout.isVisible = show
    }

    private companion object {
        private const val inputDebounceTime = 200L
        private const val minTextLength = 2
    }
}