package com.g2pdev.simpletranslator.util

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.AnimRes
import androidx.annotation.DrawableRes

fun ImageView.setImageResourceAnimated(
    @DrawableRes resId: Int,
    @AnimRes animationInResId: Int = android.R.anim.fade_out,
    @AnimRes animationOutResId: Int = android.R.anim.fade_in
) {
    val animationIn = AnimationUtils.loadAnimation(context, animationInResId)
    val animationOut = AnimationUtils.loadAnimation(context, animationOutResId)

    animationIn.fillAfter = false
    animationIn.fillBefore = false

    animationOut.fillAfter = false
    animationOut.fillBefore = false

    animationOut.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
        }

        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            setImageResource(resId)
            startAnimation(animationIn)
        }
    })

    startAnimation(animationOut)
}