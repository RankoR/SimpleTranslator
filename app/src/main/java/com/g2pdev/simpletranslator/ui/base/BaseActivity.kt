package com.g2pdev.simpletranslator.ui.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onDestroy() {
        compositeDisposable.clear()

        super.onDestroy()
    }

    protected fun Disposable.disposeOnDestroy() {
        compositeDisposable.add(this)
    }
}