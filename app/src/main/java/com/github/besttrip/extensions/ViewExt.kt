package com.github.besttrip.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

inline fun <reified T : View> T.isVisible() = visibility == View.VISIBLE

fun View.animFadeOut(duration: Long) {
    alpha = 0f
    visibility = View.VISIBLE

    animate().alpha(1f)
            .setDuration(duration / 2)
            .setListener(null)

    animate().alpha(0f)
            .setDuration(duration / 2)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    visibility = View.INVISIBLE
                }
            })
}

fun View.animFadeIn(duration: Long) {
    alpha = 1f
    visibility = View.INVISIBLE

    animate().alpha(0f)
            .setDuration(duration / 2)
            .setListener(null)

    animate().alpha(1f)
            .setDuration(duration / 2)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    visibility = View.VISIBLE
                }
            })
}