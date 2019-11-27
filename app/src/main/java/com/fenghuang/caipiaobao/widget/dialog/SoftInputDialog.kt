package com.fenghuang.caipiaobao.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatPostEvenBean
import com.hwangjr.rxbus.RxBus


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/24- 19:51
 *
 */

class SoftInputDialog(context: Context) : Dialog(context, R.style.inputDialog) {

    private var edInput: EditText? = null

    init {
        setContentView(R.layout.popup_full_screen_edit)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.BOTTOM or Gravity.BOTTOM)
        val lp = window!!.attributes
        lp.height = 0
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT// 宽度
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT  // 高度
//        lp.alpha = 1f // 透明度
        window!!.attributes = lp
        setCanceledOnTouchOutside(true)
        edInput = findViewById(R.id.etInput)
        initView()
    }

    private fun initView() {
        edInput?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty()!!) {
                    findViewById<TextView>(R.id.tvSendText).setBackgroundColor(context.resources.getColor(R.color.colorYellow))
                } else {
                    findViewById<TextView>(R.id.tvSendText).setBackgroundColor(context.resources.getColor(R.color.color_AFAFAF))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        findViewById<TextView>(R.id.tvSendText).setOnClickListener {
            if (!TextUtils.isEmpty(edInput?.text.toString())) {
                RxBus.get().post(HomeLiveChatPostEvenBean(edInput?.text.toString()))
                dismiss()
            } else {
                ToastUtils.showNormal("请输入内容")
            }
        }
    }

    override fun dismiss() {
        val view = currentFocus
        if (view is TextView) {
            val mInputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mInputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }
        super.dismiss()
    }

}