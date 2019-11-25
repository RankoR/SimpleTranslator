package com.g2pdev.simpletranslator.util

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.forEach

@SuppressLint("ClickableViewAccessibility")
fun EditText.setOnRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}

fun View.dispatchApplyWindowInsetsToChild() {
    setOnApplyWindowInsetsListener { view, insets ->
        var consumed = false

        (view as? ViewGroup)?.forEach { child ->
            val childResult = child.dispatchApplyWindowInsets(insets)
            if (childResult.isConsumed) consumed = true
        }
        if (consumed) insets.consumeSystemWindowInsets() else insets
    }
}