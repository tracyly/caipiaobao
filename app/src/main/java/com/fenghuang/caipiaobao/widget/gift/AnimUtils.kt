package com.fenghuang.caipiaobao.widget.gift

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils.loadAnimation
import com.fenghuang.caipiaobao.R


/**
 *
 * @ Author  QinTian
 * @ Date  2019/11/12- 15:47
 * @ Describe
 *
 */

object AnimUtils {


    /**
     * 获取礼物入场动画
     *
     */
    fun getInAnimation(context: Context): Animation {
        return loadAnimation(context, R.anim.gift_in)
    }

    /**
     * 获取礼物出场动画
     *
     */
    fun getOutAnimation(context: Context): AnimationSet {
        return loadAnimation(context, R.anim.gift_out) as AnimationSet
    }


}
