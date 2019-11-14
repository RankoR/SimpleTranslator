package com.g2pdev.simpletranslator.ui.mvp.base

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.g2pdev.simpletranslator.ui.MainActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatFragment

abstract class BaseMvpFragment : MvpAppCompatFragment() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onDestroyView() {
        compositeDisposable.clear()

        super.onDestroyView()
    }

    protected fun Disposable.disposeOnDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    protected fun findRootNavController(): NavController? {
        return (activity as MainActivity).navHostFragment.findNavController()
    }

}