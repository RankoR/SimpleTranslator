package com.g2pdev.simpletranslator.ui.mvp.base

import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment() {

    abstract fun getFragmentTag(): String

    fun show(fragmentManager: FragmentManager?) {
        fragmentManager?.let {
            show(it, getFragmentTag())
        }
    }

}