package com.fenghuang.caipiaobao.widget.gift

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.OvershootInterpolator


/**
 *
 * @ Author  QinTian
 * @ Date  2019/11/12- 15:49
 * @ Describe
 *
 */
class NumAnim {


    private var lastAnimator: Animator? = null

    fun start(view: View) {
        if (lastAnimator != null) {
            lastAnimator!!.removeAllListeners()
            lastAnimator!!.end()
            lastAnimator!!.cancel()
        }
        val animX = ObjectAnimator.ofFloat(view, "scaleX", 1.6f, 1.0f)
        val animY = ObjectAnimator.ofFloat(view, "scaleY", 1.6f, 1.0f)
        val animSet = AnimatorSet()
        lastAnimator = animSet
        animSet.duration = 400
        animSet.interpolator = OvershootInterpolator()
        animSet.playTogether(animX, animY)
        animSet.start()
    }

}