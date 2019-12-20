package com.fenghuang.caipiaobao.ui.widget.popup

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.TextView
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R

/**
 *  author : Peter
 *  date   : 2019/10/16 15:34
 *  desc   : 直播间余额不足提示充值的窗口
 */
class ReChargePopup(context: Context) : Dialog(context) {

    init {
        setContentView(R.layout.popup_live_chat_recharge)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window!!.attributes
        lp.width = ViewUtils.dp2px(314) // 宽度
        lp.height = ViewUtils.dp2px(290)  // 高度
        window!!.attributes = lp
        window!!.setWindowAnimations(R.style.dialogAnim)
        findViewById<TextView>(R.id.tvRechargeCancel).setOnClickListener {
            dismiss()
        }
        findViewById<TextView>(R.id.tvGoRecharge).setOnClickListener {
            dismiss()
            mListener?.invoke()
        }
    }


    private var mListener: (() -> Unit)? = null
    fun setOnSendClickListener(listener: () -> Unit) {
        mListener = listener
    }

    private fun addBackground() {
        val attributes = (context as Activity).window.attributes
        attributes.alpha = 0.7f
        (context as Activity).window.attributes = attributes
        this.setOnDismissListener {
            val attributes = (context as Activity).window.attributes
            attributes.alpha = 1f
            (context as Activity).window.attributes = attributes
        }
    }
}