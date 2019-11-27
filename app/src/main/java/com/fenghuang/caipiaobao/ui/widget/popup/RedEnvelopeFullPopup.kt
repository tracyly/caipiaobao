package com.fenghuang.caipiaobao.ui.widget.popup

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.popup_live_room_red_envelope_full.*

/**
 *  author : Peter
 *  date   : 2019/10/14 15:34
 *  desc   : 直播间发红包的窗口
 */
class RedEnvelopeFullPopup(context: Context) : Dialog(context, R.style.inputDialog) {
    val etRedETotal: EditText
    val etRedRedNumber: EditText
    val etRedContent: EditText
    val etRedEnvelopeSend: TextView

    init {
        val lp = window!!.attributes
        lp.width = ViewUtils.dp2px(290)
        lp.height = ViewUtils.dp2px(310)
        window!!.attributes = lp
        setContentView(R.layout.popup_live_room_red_envelope_full)
//        addBackground()
        etRedETotal = etRedEnvelopeTotal
        etRedRedNumber = etRedEnvelopeRedNumber
        etRedContent = etRedEnvelopeContent
        etRedEnvelopeSend = tvRedSend
        etRedEnvelopeSend.setOnClickListener {
            val total = if (etRedETotal.text.toString().isNotEmpty()) etRedETotal.text.toString() else etRedETotal.hint.toString()
            val number = if (etRedRedNumber.text.toString().isNotEmpty()) etRedRedNumber.text.toString() else etRedRedNumber.hint.toString()
            val content = if (etRedContent.text.toString().isNotEmpty()) etRedContent.text.toString() else etRedContent.hint.toString()
            mListener?.invoke(total, number, content)
        }

        rootRed.setOnClickListener {
            val view = currentFocus
            if (view is TextView) {
                val mInputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
            }
        }
    }


    private var mListener: ((total: String, redNumber: String, redContent: String) -> Unit)? = null
    fun setOnSendClickListener(listener: (total: String, redNumber: String, redContent: String) -> Unit) {
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