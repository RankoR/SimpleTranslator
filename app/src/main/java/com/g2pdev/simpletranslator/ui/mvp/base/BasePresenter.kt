package com.g2pdev.simpletranslator.ui.mvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<T> : MvpPresenter<T>() where T : MvpView {

    private val compositeDisposable by lazy { CompositeDisposable() }

    protected fun dispose() {
        compositeDisposable.dispose()
    }

    protected fun clear() {
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose()
    }

    protected fun Disposable.disposeOnPresenterDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}