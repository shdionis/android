package ru.montgolfiere.searchquest.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import ru.montgolfiere.searchquest.R
import ru.montgolfiere.searchquest.animations.CustomAnimationListener
import ru.montgolfiere.searchquest.model.QuestStep
import ru.montgolfiere.searchquest.utils.ImageUtils

class QuestStepSwitchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val sealImage: ImageView
    private val appearAnim: Animation
    private val disappearAnim: Animation
    private var state: Int = ANIMATION_IS_FINISHED
    init {
        inflate(context, R.layout.loading_view, this)
        sealImage = findViewById(R.id.quest_stub_image)
        appearAnim = AnimationUtils.loadAnimation(context, R.anim.seal_appear_animation)
        disappearAnim = AnimationUtils.loadAnimation(context, R.anim.seal_disappear_animation)
        disappearAnim.setAnimationListener(
            CustomAnimationListener(
                endCallback = {
                    state = ANIMATION_IS_FINISHED
                    hide()
                }
            )
        )
    }

    fun hide() {
        if (state == ANIMATION_IS_FINISHED) {
            visibility = View.GONE
            sealImage.clearAnimation()
        }
    }

    fun show(finishedStep: QuestStep) {
        Log.d(TAG, "show = ${finishedStep.id}")
        val brokenSealImageId = ImageUtils.getBrokenSealDrawableId(context, finishedStep.id)
        val sealImageId = ImageUtils.getSealDrawableId(context, finishedStep.id)
        sealImage.setImageResource(sealImageId)
        state = ANIMATION_IN_PROGRESS
        appearAnim.setAnimationListener(
            CustomAnimationListener(
                endCallback = {
                    sealImage.setImageResource(brokenSealImageId)
                    sealImage.startAnimation(disappearAnim)
                }
            )
        )
        sealImage.startAnimation(appearAnim)
        visibility = View.VISIBLE
    }

    fun isAnimationInProgress(): Boolean {
        return state == ANIMATION_IN_PROGRESS
    }

    companion object {
        private const val TAG = "QuestStepSwitchView"
        private const val ANIMATION_IN_PROGRESS = 1
        private const val ANIMATION_IS_FINISHED = 0
    }
}
