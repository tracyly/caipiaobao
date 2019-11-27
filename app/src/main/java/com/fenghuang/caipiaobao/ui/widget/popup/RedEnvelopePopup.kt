package com.fenghuang.caipiaobao.ui.widget.popup

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.widget.popup.BasePopupWindow
import com.fenghuang.caipiaobao.R

/**
 *  author : Peter
 *  date   : 2019/10/14 15:34
 *  desc   : 直播间发红包的窗口
 */
class RedEnvelopePopup(context: Context) : BasePopupWindow(context, R.layout.popup_live_room_red_envelope) {
    val etRedEnvelopeTotal: EditText
    val etRedEnvelopeRedNumber: EditText
    val etRedEnvelopeContent: EditText
    val etRedEnvelopeSend: TextView

    init {
//        width = ViewUtils.getScreenWidth()
        addBackground()
        isFocusable = true
        etRedEnvelopeTotal = findView(R.id.etRedEnvelopeTotal)
        etRedEnvelopeRedNumber = findView(R.id.etRedEnvelopeRedNumber)
        etRedEnvelopeContent = findView(R.id.etRedEnvelopeContent)
        etRedEnvelopeSend = findView(R.id.tvRedSend)
        etRedEnvelopeSend.setOnClickListener {
            val total = if (etRedEnvelopeTotal.text.toString().isNotEmpty()) etRedEnvelopeTotal.text.toString() else etRedEnvelopeTotal.hint.toString()
            val number = if (etRedEnvelopeRedNumber.text.toString().isNotEmpty()) etRedEnvelopeRedNumber.text.toString() else etRedEnvelopeRedNumber.hint.toString()
            val content = if (etRedEnvelopeContent.text.toString().isNotEmpty()) etRedEnvelopeContent.text.toString() else etRedEnvelopeContent.hint.toString()
            mListener?.invoke(total, number, content)
        }
    }


    private var mListener: ((total: String, redNumber: String, redContent: String) -> Unit)? = null
    fun setOnSendClickListener(listener: (total: String, redNumber: String, redContent: String) -> Unit) {
        mListener = listener
    }

    fun setOnSendClickListener() {
        val etRedEnvelopeTotal = findView<EditText>(R.id.etRedEnvelopeTotal)
        val etRedEnvelopeRedNumber = findView<EditText>(R.id.etRedEnvelopeRedNumber)
        findView<TextView>(R.id.tvRedSend).setOnClickListener {
            if (!TextUtils.isEmpty(etRedEnvelopeTotal.text.toString())) {
                if (!TextUtils.isEmpty(etRedEnvelopeRedNumber.text.toString())) {

                } else ToastUtils.showNormal("请输入红包个数")
            } else ToastUtils.showNormal("请输入金额")

        }
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