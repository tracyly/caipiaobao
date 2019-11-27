package com.fenghuang.caipiaobao.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.widget.EditText
import kotlinx.android.synthetic.main.dialog_pass_word.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/17- 14:53
 * @ Describe : 密码输入
 *
 */


class PassWordFullDialog(context: Context) : Dialog(context) {

    init {
        setContentView(com.fenghuang.caipiaobao.R.layout.dialog_pass_word_full)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.BOTTOM)
        setCanceledOnTouchOutside(false)
        showSoftInputFromWindow(edtPassWord)
        initDialog()
    }

    private fun initDialog() {
        relCloseDialog.setOnClickListener {
            hideKeyboard()
            dismiss()
        }
    }

    fun setTextWatchListener(textWatcher: TextWatcher) {
        edtPassWord.addTextChangedListener(textWatcher)
    }

    fun showTipsText(string: String) {
        tvFalseTip.visibility = View.VISIBLE
        tvFalseTip.text = string
    }

    override fun dismiss() {
        hideKeyboard()
        super.dismiss()
    }


    //显示软键盘
    private fun showSoftInputFromWindow(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(0, HIDE_NOT_ALWAYS)
    }
}