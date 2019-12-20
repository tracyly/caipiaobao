package com.fenghuang.caipiaobao.ui.widget.popup

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.utils.FastClickUtils
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
        setContentView(R.layout.popup_live_room_red_envelope_full)
//        addBackground()
        etRedETotal = etRedEnvelopeTotal
        etRedRedNumber = etRedEnvelopeRedNumber
        etRedContent = etRedEnvelopeContent
        etRedEnvelopeSend = tvRedSend
        etRedEnvelopeSend.setOnClickListener {
            if (FastClickUtils.isFastClick()) {

                val total = if (etRedETotal.text.toString().isNotEmpty()) etRedETotal.text.toString() else ""
                val number = if (etRedRedNumber.text.toString().isNotEmpty()) etRedRedNumber.text.toString() else ""
                val content = if (etRedContent.text.toString().isNotEmpty()) etRedContent.text.toString() else ""
            mListener?.invoke(total, number, content)
            }
        }

        rootRed.setOnClickListener {
            if (FastClickUtils.isFastClick()) {

                val view = currentFocus
                if (view is TextView) {
                    val mInputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    mInputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
                }
            }
        }


        etRedEnvelopeTotal.addTextChangedListener(object : TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString() != "" && p0.toString().toInt() > 2000) {
                    ToastUtils.showNormal("最大金额为2000元")
                    etRedEnvelopeTotal.setText("")
                }
                if (p0.toString() != "" && p0.toString().toInt() < 1) {
                    ToastUtils.showNormal("最小金额为1元")
                    etRedEnvelopeTotal.setText("")
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        etRedEnvelopeRedNumber.addTextChangedListener(object : TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString() != "" && p0.toString().toInt() > 200) {
                    ToastUtils.showNormal("红包最多个数为200")
                    etRedEnvelopeRedNumber.setText("")
                }
                if (p0.toString() != "" && p0.toString().toInt() < 1) {
                    ToastUtils.showNormal("红包最少个数为1")
                    etRedEnvelopeRedNumber.setText("")
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
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