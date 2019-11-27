package com.fenghuang.caipiaobao.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import kotlinx.android.synthetic.main.dialog_gift_send.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/25- 13:50
 * @ Describe 成功dialog
 *
 */

class GiftSendDialog(context: Context, private val des: String) : Dialog(context) {
    init {
        setContentView(com.fenghuang.caipiaobao.R.layout.dialog_gift_send)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window!!.attributes
        lp.width = ViewUtils.dp2px(316) // 宽度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        setCanceledOnTouchOutside(false)
        initText()
        initEvent()
    }

    private fun initText() {
        tvSendGiftText.text = des
    }

    private fun initEvent() {
        cbNoTipsNext.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                UserInfoSp.putSendGiftTips(true)
            } else {
                UserInfoSp.putSendGiftTips(false)
            }
        }

        if (tvConfirm !== null) {
            tvConfirm.setOnClickListener {
                dismiss()
                mListener?.invoke()
            }
        }

        if (tvCanceled !== null) {
            tvCanceled.setOnClickListener {
                dismiss()
            }
        }
    }

    private var mListener: (() -> Unit)? = null
    fun setConfirmClickListener(listener: () -> Unit) {
        mListener = listener
    }

}