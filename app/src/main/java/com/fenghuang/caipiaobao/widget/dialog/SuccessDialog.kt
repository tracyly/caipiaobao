package com.fenghuang.caipiaobao.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import com.fenghuang.baselib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_success.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/25- 13:50
 * @ Describe 成功dialog
 *
 */

class SuccessDialog(context: Context, content: String, img: Int) : Dialog(context) {
    init {
        setContentView(com.fenghuang.caipiaobao.R.layout.dialog_success)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window!!.attributes
        lp.width = ViewUtils.dp2px(190) // 宽度
        lp.height = ViewUtils.dp2px(80)  // 高度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        setCanceledOnTouchOutside(false)
        initText(content, img)
    }

    private fun initText(content: String, img: Int) {
        tvDialogText.text = content
        ivDialogImg.setImageResource(img)
    }
}