package com.fenghuang.caipiaobao.ui.widget.popup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.*
import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.widget.popup.BasePopupWindow
import com.fenghuang.caipiaobao.R
import java.util.regex.Pattern

/**
 *  author : Peter
 *  date   : 2019/10/14 15:34
 *  desc   : 直播间发红包的窗口
 */
class RedEnvelopePopup(context: Context) : BasePopupWindow(context, R.layout.popup_live_room_red_envelope) {
    private val etRedEnvelopeTotal: EditText
    private val etRedEnvelopeRedNumber: EditText
    private val etRedEnvelopeContent: EditText
    private val etRedEnvelopeSend: TextView

    init {
//        width = ViewUtils.getScreenWidth()
        addBackground()
        isFocusable = true
        etRedEnvelopeTotal = findView(R.id.etRedEnvelopeTotal)
        etRedEnvelopeRedNumber = findView(R.id.etRedEnvelopeRedNumber)
        etRedEnvelopeContent = findView(R.id.etRedEnvelopeContent)

        etRedEnvelopeSend = findView(R.id.tvRedSend)
        etRedEnvelopeSend.setOnClickListener {
            val total = if (etRedEnvelopeTotal.text.toString().isNotEmpty()) etRedEnvelopeTotal.text.toString() else ""
            val number = if (etRedEnvelopeRedNumber.text.toString().isNotEmpty()) etRedEnvelopeRedNumber.text.toString() else ""
            val content = if (etRedEnvelopeContent.text.toString().isNotEmpty()) etRedEnvelopeContent.text.toString() else ""
            mListener?.invoke(total, number, content)
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

        val inputFilter: InputFilter = object : InputFilter {
            var pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]")
            override fun filter(charSequence: CharSequence, i: Int, i1: Int, spanned: Spanned, i2: Int, i3: Int): CharSequence? {
                val matcher = pattern.matcher(charSequence)
                return if (!matcher.find()) {
                    null
                } else {
                    ToastUtils.showNormal("只能输入汉字,英文，数字")
                    ""
                }
            }
        }
        etRedEnvelopeContent.filters = arrayOf(inputFilter)
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