package com.g2pdev.simpletranslator.ui.mvp.base

import androidx.fragment.app.FragmentManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpBottomSheetDialogFragment

abstract class BaseMvpBottomSheetFragment : MvpBottomSheetDialogFragment() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    abstract fun getFragmentTag(): String

    fun show(fragmentManager: FragmentManager?) {
        fragmentManager?.let {
            show(it, getFragmentTag())
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clear()

        super.onDestroyView()
    }

    protected fun Disposable.disposeOnDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }

}