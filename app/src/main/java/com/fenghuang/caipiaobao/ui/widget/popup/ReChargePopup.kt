package com.fenghuang.caipiaobao.ui.widget.popup

import android.app.Activity
import android.content.Context
import android.widget.TextView
import com.fenghuang.baselib.widget.popup.BasePopupWindow
import com.fenghuang.caipiaobao.R

/**
 *  author : Peter
 *  date   : 2019/10/16 15:34
 *  desc   : 直播间余额不足提示充值的窗口
 */
class ReChargePopup(context: Context) : BasePopupWindow(context, R.layout.popup_live_chat_recharge) {

    init {
//        width = ViewUtils.getScreenWidth()
        addBackground()
        isFocusable = true
        findView<TextView>(R.id.tvRechargeCancel).setOnClickListener {
            dismiss()
        }
        findView<TextView>(R.id.tvGoRecharge).setOnClickListener {
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