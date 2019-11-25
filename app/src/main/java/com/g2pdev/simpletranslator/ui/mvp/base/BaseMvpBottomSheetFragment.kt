package com.g2pdev.simpletranslator.ui.mvp.base

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.g2pdev.simpletranslator.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpDelegate

abstract class BaseMvpBottomSheetFragment : DialogFragment() {

    protected open val isFullscreen = false

    private val compositeDisposable by lazy { CompositeDisposable() }
    private val mvpDelegate: MvpDelegate<out BaseMvpBottomSheetFragment> by lazy { MvpDelegate(this) }

    abstract fun getFragmentTag(): String

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    fun show(fragmentManager: FragmentManager?) {
        fragmentManager?.let {
            if (it.findFragmentByTag(getFragmentTag()) == null) {
                show(it, getFragmentTag())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mvpDelegate.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getWindowHeight()
        )

        mvpDelegate.onAttach()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        mvpDelegate.onDetach()

        super.onDestroyView()
    }

    override fun onDestroy() {
        if (isRemoving || activity?.isFinishing == true) {
            mvpDelegate.onDestroy()
        }

        super.onDestroy()
    }

    protected fun Disposable.disposeOnDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    private fun getWindowHeight() = Resources.getSystem().displayMetrics.heightPixels

}