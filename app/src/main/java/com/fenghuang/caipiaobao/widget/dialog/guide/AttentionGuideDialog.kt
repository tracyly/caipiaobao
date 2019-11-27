package com.fenghuang.caipiaobao.widget.dialog.guide

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.RelativeLayout
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.dialog_guide_attention.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/31- 18:32
 * @ Describe 第一次提示的Dialog
 *
 */

class AttentionGuideDialog(context: Context) : Dialog(context) {


    init {
        setContentView(R.layout.dialog_guide_attention)
        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = window!!.attributes
        lp.width = ViewUtils.getScreenWidth()  // 宽度
        lp.height = ViewUtils.getScreenHeight()  // 高度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        initView()
    }

    private fun initView() {
//        LogUtils.e("------>"+ViewUtils.getStatusHeight().toString())
        val lp = imgFirstAttentionView.layoutParams as RelativeLayout.LayoutParams
        lp.setMargins(ViewUtils.dp2px(100), ViewUtils.getStatusHeight() + ViewUtils.dp2px(26), 0, 0)

        imgDisMiss.setOnClickListener {
            dismiss()
        }
    }


}