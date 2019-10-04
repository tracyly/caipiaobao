package com.fenghuang.caipiaobao.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.dialog_fonfirm.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/8/25- 13:17
 * @ Describe dialog
 *
 */
class DialogConfirm(context: Context?, content: String, confirm: String, cancel: String) : Dialog(context) {
    init {
        setContentView(com.fenghuang.caipiaobao.R.layout.dialog_fonfirm)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.TOP)
        initText(content, confirm, cancel)
    }

    private fun initText(content: String, confirm: String, cancel: String) {
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
    }


    fun setOnConfirmclickListener(listener: View.OnClickListener) {
        if (tvConfirm !== null) {
            tvConfirm.setOnClickListener(listener)
        }
    }

    fun setOnCanCelclickListener(listener: View.OnClickListener) {
        if (tvCancel !== null) {
            tvCancel.setOnClickListener(listener)
        }
    }
}
