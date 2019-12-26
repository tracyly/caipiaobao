package com.fenghuang.caipiaobao.widget.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatBeanNew
import com.fenghuang.caipiaobao.utils.UserInfoSp
import kotlinx.android.synthetic.main.dialog_forbidden_words.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/24- 19:51
 * @ Describe 钻石兑换
 *
 */

class ForbiddenWordsDialog(context: Context, var data: HomeLiveChatBeanNew) : Dialog(context) {


    private var banTime = 30

    init {
        setContentView(R.layout.dialog_forbidden_words)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window!!.attributes
        lp.width = ViewUtils.dp2px(316) // 宽度
        lp.height = ViewUtils.dp2px(200)  // 高度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        setCanceledOnTouchOutside(false)
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        imgForbiddenClose.setOnClickListener {
            dismiss()
        }
        tvForbiddenName.text = "禁言 “" + data.userName + "”"

        linForbidden30Min.setOnClickListener {
            cbForbidden30Min.isChecked = true
            cbForbiddenForever.isChecked = false
            banTime = 30
        }
        linForbiddenForever.setOnClickListener {
            cbForbidden30Min.isChecked = false
            cbForbiddenForever.isChecked = true
            banTime = 0
        }
        tvForbiddenConfirm.setOnClickListener {
            forbidden()
        }
    }


    private fun forbidden() {
        val dialog = LoadingDialog(context)
        dialog.show()
        HomeApi.forBiddenWords(UserInfoSp.getUserId(), data.user_id.toInt(), data.room_id.toInt(), banTime) {
            onSuccess {
                val ban = if (banTime == 0) "永久禁言" else "禁言30分钟"
                ToastUtils.show(data.userName + " 成功 " + ban)
                dialog.dismiss()
                dismiss()
            }
            onFailed {
                ToastUtils.show(it.getMsg()!!)
                dialog.dismiss()
            }
        }
    }
}