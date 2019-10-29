package com.fenghuang.caipiaobao.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.Gravity
import com.fenghuang.baselib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_invest.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/24- 19:51
 * @ Describe 充值dialog
 *
 */

class InvestDialog(context: Context, title: String, confirm: String, cancel: String) : Dialog(context) {
    init {
        setContentView(com.fenghuang.caipiaobao.R.layout.dialog_invest)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window!!.attributes
        lp.width = ViewUtils.dp2px(316) // 宽度
        lp.height = ViewUtils.dp2px(238)  // 高度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        setCanceledOnTouchOutside(false)
        initDialog(title, confirm, cancel)
    }

    private fun initDialog(title: String, confirm: String, cancel: String) {
        if (tvConfirm !== null) {
            tvConfirm.setOnClickListener {
                mListener?.invoke()
            }
        }
        if (tvCancel !== null) {
            tvCancel.setOnClickListener {
                dismiss()
            }
        }
        if (!TextUtils.isEmpty(title)) {
            tvInvestType.text = title
        }
        if (!TextUtils.isEmpty(confirm)) {
            tvConfirm.text = confirm
        }
        if (!TextUtils.isEmpty(cancel)) {
            tvCancel.text = cancel
        }
    }


    private var mListener: (() -> Unit)? = null
    fun setConfirmClickListener(listener: () -> Unit) {
        mListener = listener
    }

    fun getText(): String {
        return etInvestMoney.text.toString()
    }
}