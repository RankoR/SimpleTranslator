package com.g2pdev.simpletranslator.ui.mvp.language.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.g2pdev.simpletranslator.R
import com.g2pdev.translation.translation.language.LanguagePair
import com.g2pdev.simpletranslator.ui.mvp.base.BaseBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_dialog_model_download_required.*

class ModelDownloadRequiredFragment : BaseBottomSheetFragment() {

    var onDownloadClickListener: (() -> Unit)? = null

    override fun getFragmentTag() = constantTag

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_model_download_required, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<LanguagePair>(keyLanguagePair)?.let { languagePair ->
            messageTv.text = getString(R.string.message_model_dialog_required, languagePair.source.name, languagePair.target.name)
        } ?: dismiss()

        downloadBtn.setOnClickListener {
            onDownloadClickListener?.invoke()
            dismiss()
        }
    }

    companion object {
        private const val constantTag = "ModelDownloadRequiredFragment"
        private const val keyLanguagePair = "language_pair"

        fun newInstance(languagePair: LanguagePair): ModelDownloadRequiredFragment {
            val fragment = ModelDownloadRequiredFragment()
            fragment.arguments = bundleOf(keyLanguagePair to languagePair)

            return fragment
        }
    }

}