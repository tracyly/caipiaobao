package com.fenghuang.caipiaobao.widget.dialog.guide

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.RelativeLayout
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.dialog_guide_home.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/31- 18:32
 * @ Describe 第一次提示的Dialog
 *
 */

class HomeGuideDialog(context: Context, val activity: Activity) : Dialog(context) {


    init {
        setContentView(R.layout.dialog_guide_home)
        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = window!!.attributes
        lp.width = ViewUtils.getScreenWidth()  // 宽度
        lp.height = ViewUtils.getScreenHeight()  // 高度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        initView()
        findViewById<ImageView>(R.id.imgSecondView).setOnClickListener {
            dismiss()
        }
    }

    private fun initView() {
        val lp = imgThird.layoutParams as RelativeLayout.LayoutParams
        lp.setMargins(0, 0, 0, ViewUtils.getBottomNavigationBarHeight(activity) + ViewUtils.dp2px(70))
    }

}