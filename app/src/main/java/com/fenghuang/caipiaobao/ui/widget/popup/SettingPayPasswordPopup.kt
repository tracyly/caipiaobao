package com.fenghuang.caipiaobao.ui.widget.popup

import android.app.Activity
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.baselib.widget.popup.BasePopupWindow
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.utils.FastClickUtils

/**
 *  author : Peter
 *  date   : 2019/10/16 15:34
 *  desc   : 提示设置支付密码
 */
class SettingPayPasswordPopup(context: Context) : BasePopupWindow(context, R.layout.popup_live_chat_setting_password) {

    init {
        width = ViewUtils.dp2px(280)
        addBackground()
        isFocusable = true
        val etPayPassword = findView<EditText>(R.id.etPayPassword)
        val etOkPassword = findView<EditText>(R.id.etOkPassword)
        findView<TextView>(R.id.tvRedSend).setOnClickListener {
            if (FastClickUtils.isFastClick()) {

                mListener?.invoke(etPayPassword.text.toString().trim(), etOkPassword.text.toString().trim())
            }
        }
    }


    private var mListener: ((password: String, passwordOk: String) -> Unit)? = null
    fun setOnSendClickListener(listener: (password: String, passwordOk: String) -> Unit) {
        mListener = listener
    }

    private fun addBackground() {
        val attributes = (context as Activity).window.attributes
        attributes.alpha = 0.7f
        (context as Activity).window.attributes = attributes
        this.setOnDismissListener {
            val attributes = (context as Activity).window.attributes
            attributes.alpha = 1f
            (context as Activity).window.attributes = attributes
        }
    }
}