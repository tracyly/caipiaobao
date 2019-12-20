package com.fenghuang.caipiaobao.widget.gift

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.LinearInterpolator
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.widget.anim.CustomRotateAnim


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

    /**
     * 首冲动画
     */
    fun getFirstRechargeAnimation(view: View) {
        // 获取自定义动画实例
        val rotateAnim = CustomRotateAnim.getCustomRotateAnim()
        // 一次动画执行1秒
        rotateAnim?.duration = 700
        // 设置为循环播放
        rotateAnim?.repeatCount = -1
        // 设置为匀速
        rotateAnim?.interpolator = LinearInterpolator()
        // 开始播放动画
        view.startAnimation(rotateAnim)
    }


}
