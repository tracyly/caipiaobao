package com.fenghuang.caipiaobao.ui.bet

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.widget.RelativeLayout
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_bet.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/8/28- 11:29
 * @ Describe 投注页
 *
 */

class BetFragment : BaseNavFragment() {


    private var mRightOutSet: AnimatorSet? = null
    private var mLeftInSet: AnimatorSet? = null
    private var mIsShowBack: Boolean = false

    override fun getPageTitle() = getString(R.string.tab_betting)

    override fun isShowBackIcon() = false

    override fun getContentResID() = R.layout.fragment_bet

    override fun initContentView() {
        setAnimators() // 设置动画
        setCameraDistance() // 设置镜头距离
    }


    private fun setAnimators() {
        mRightOutSet = AnimatorInflater.loadAnimator(context, R.animator.anim_out) as AnimatorSet
        mLeftInSet = AnimatorInflater.loadAnimator(context, R.animator.anim_in) as AnimatorSet

        mRightOutSet!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                main_fl_container.isClickable = false
            }
        })
        mLeftInSet!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                main_fl_container.isClickable = true
            }
        })
    }

    // 距离
    private fun setCameraDistance() {
        val distance = 16000
        val scale = resources.displayMetrics.density * distance
        findView<RelativeLayout>(R.id.main_fl_card_front).cameraDistance = scale
        findView<RelativeLayout>(R.id.main_fl_card_back).cameraDistance = scale
    }


    override fun initEvent() {
        main_fl_container.setOnClickListener {
            // 正面
            mIsShowBack = if (!mIsShowBack) {
                mRightOutSet?.setTarget(main_fl_card_front)
                mLeftInSet?.setTarget(main_fl_card_back)
                mRightOutSet?.start()
                mLeftInSet?.start()
                true
                // 反面
            } else {
                mRightOutSet?.setTarget(main_fl_card_back)
                mLeftInSet?.setTarget(main_fl_card_front)
                mRightOutSet?.start()
                mLeftInSet?.start()
                false
            }
        }
    }


}