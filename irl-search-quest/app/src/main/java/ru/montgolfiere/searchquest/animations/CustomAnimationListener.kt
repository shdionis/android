package ru.montgolfiere.searchquest.animations

import android.view.animation.Animation

class CustomAnimationListener(
    private val startCallback: (animation: Animation?) -> Unit = {},
    private val endCallback: (animation: Animation?) -> Unit = {},
    private val repeatCallback: (animation: Animation?) -> Unit = {},
) : Animation.AnimationListener {
    override fun onAnimationStart(animation: Animation?) {
        startCallback(animation)
    }

    override fun onAnimationEnd(animation: Animation?) {
        endCallback(animation)
    }

    override fun onAnimationRepeat(animation: Animation?) {
        repeatCallback(animation)
    }
}