package com.fenghuang.caipiaobao.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.fenghuang.baselib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_fonfirm.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/17- 14:53
 * @ Describe 退出Dialog
 *
 */


class ExitDialog(context: Context, title: String, confirm: String, cancel: String, confirmClickListener: View.OnClickListener, canCelClickListener: View.OnClickListener) : Dialog(context) {

    init {
        setContentView(com.fenghuang.caipiaobao.R.layout.dialog_exit)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window!!.attributes
        lp.width = ViewUtils.dp2px(316) // 宽度
        lp.height = ViewUtils.dp2px(146)  // 高度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        initText(title, confirm, cancel, confirmClickListener, canCelClickListener)
    }

    private fun initText(content: String, confirm: String, cancel: String, confirmClickListener: View.OnClickListener, canCelClickListener: View.OnClickListener) {
        if (!TextUtils.isEmpty(content)) {
            tvContent.visibility = View.VISIBLE
            tvContent.text = content
        }
        if (!TextUtils.isEmpty(confirm)) {
            tvConfirm.visibility = View.VISIBLE
            tvConfirm.text = confirm
        }
        if (!TextUtils.isEmpty(cancel)) {
            tvCancel.visibility = View.VISIBLE
            tvCancel.text = cancel
        }
        if (tvConfirm !== null) {
            tvConfirm.setOnClickListener(confirmClickListener)
        }
        if (tvCancel !== null) {
            tvCancel.setOnClickListener(canCelClickListener)
        }
    }
}