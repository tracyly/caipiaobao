package com.fenghuang.caipiaobao.widget.dialog.guide

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/17- 14:53
 * @ Describe : 密码输入
 *
 */


class GiftFullDialog(context: Context) : Dialog(context) {

    init {
        setContentView(com.fenghuang.caipiaobao.R.layout.dialog_chat_bottom_gif_full)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = window!!.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT // 宽度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        window!!.setGravity(Gravity.BOTTOM)

    }

}