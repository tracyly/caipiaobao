package com.fenghuang.caipiaobao.ui.widget.popup

import android.app.Activity
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.widget.popup.BasePopupWindow
import com.fenghuang.caipiaobao.R

/**
 *  author : Peter
 *  date   : 2019/10/14 15:34
 *  desc   : 直播间发红包的窗口
 */
class RedEnvelopePopup(context: Context) : BasePopupWindow(context, R.layout.popup_live_room_red_envelope) {

    init {
//        width = ViewUtils.getScreenWidth()
        addBackground()
        isFocusable = true
        val etRedEnvelopeTotal = findView<EditText>(R.id.etRedEnvelopeTotal)
        val etRedEnvelopeRedNumber = findView<EditText>(R.id.etRedEnvelopeRedNumber)
        val etRedEnvelopeContent = findView<EditText>(R.id.etRedEnvelopeContent)
        val etRedEnvelopePassword = findView<EditText>(R.id.etRedEnvelopePassword)
        findView<TextView>(R.id.tvRedSend).setOnClickListener {
            val total = if (etRedEnvelopeTotal.text.toString().isNotEmpty()) etRedEnvelopeTotal.text.toString() else etRedEnvelopeTotal.hint.toString()
            val number = if (etRedEnvelopeRedNumber.text.toString().isNotEmpty()) etRedEnvelopeRedNumber.text.toString() else etRedEnvelopeRedNumber.hint.toString()
            val content = if (etRedEnvelopeContent.text.toString().isNotEmpty()) etRedEnvelopeContent.text.toString() else etRedEnvelopeContent.hint.toString()
            val password = etRedEnvelopePassword.text.toString()
            mListener?.invoke(total, number, content, password)
        }
    }


    private var mListener: ((total: String, redNumber: String, redContent: String, password: String) -> Unit)? = null
    fun setOnSendClickListener(listener: (total: String, redNumber: String, redContent: String, password: String) -> Unit) {
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