package com.g2pdev.simpletranslator.ui.mvp.base

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.ui.MainActivity
import com.g2pdev.simpletranslator.util.dispatchApplyWindowInsetsToChild
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatFragment

abstract class BaseMvpFragment(
    private val lightStatusBar: Boolean = false,
    private val lightNavigationBar: Boolean = false
) : MvpAppCompatFragment() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onDestroyView() {
        compositeDisposable.clear()

        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.requestApplyInsets(view)
        view.dispatchApplyWindowInsetsToChild()
        //applyStyle()
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            onApplyWindowsInsets(insets.systemWindowInsetTop)
            insets
        }
    }

    open fun onApplyWindowsInsets(statusBarHeight: Int) {
    }

    protected fun Disposable.disposeOnDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    protected fun findRootNavController(): NavController? {
        return (activity as MainActivity).navHostFragment.findNavController()
    }

    private fun applyStyle() {
        activity?.window?.let { window ->
            val decorView = window.decorView
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                when {
                    lightNavigationBar && lightStatusBar -> {
                        decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.colorWhite)
                    }
                    lightNavigationBar && !lightStatusBar -> {
                        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.colorWhite)
                    }
                    !lightNavigationBar && lightStatusBar -> {
                        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                    }
                    !lightNavigationBar && !lightStatusBar -> {
                        decorView.systemUiVisibility = 0
                        window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                    }
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (lightStatusBar) {
                    decorView.systemUiVisibility =
                        decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    decorView.systemUiVisibility = 0
                }
            }
        }
    }

}