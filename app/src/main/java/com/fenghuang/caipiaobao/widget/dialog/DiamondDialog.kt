package com.fenghuang.caipiaobao.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import com.fenghuang.baselib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_diamond.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/24- 19:51
 * @ Describe 钻石兑换
 *
 */

class DiamondDialog(context: Context) : Dialog(context) {
    init {
        setContentView(com.fenghuang.caipiaobao.R.layout.dialog_diamond)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window!!.attributes
        lp.width = ViewUtils.dp2px(316) // 宽度
        lp.height = ViewUtils.dp2px(238)  // 高度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        setCanceledOnTouchOutside(false)
        initDialog()
    }

    private fun initDialog() {
        if (tvConfirmChange !== null) {
            tvConfirmChange.setOnClickListener {
                mListener?.invoke()
            }
        }
        if (imgClose !== null) {
            imgClose.setOnClickListener {
                dismiss()
            }
        }

    }


    private var mListener: (() -> Unit)? = null
    fun setConfirmClickListener(listener: () -> Unit) {
        mListener = listener
    }
}