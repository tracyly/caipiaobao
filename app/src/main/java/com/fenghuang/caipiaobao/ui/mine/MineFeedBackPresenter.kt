package com.fenghuang.caipiaobao.ui.mine

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 19:15
 * @ Describe
 *
 */

class MineFeedBackPresenter : BaseMvpPresenter<MineFeedBackFragment>() {

    fun initEditFeedBack(editText: EditText, textView: TextView) {
        val num = 0
        val mMaxNum = 500
        editText.addTextChangedListener(object : TextWatcher {
            //记录输入的字数
            var wordNum: CharSequence? = null
            var selectionStart: Int = 0
            var selectionEnd: Int = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                wordNum = s
            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                val number = num + s!!.length
                textView.text = "$number/500"
                selectionStart = editText.selectionStart
                selectionEnd = editText.selectionEnd
                //判断大于最大值
                if (wordNum!!.length > mMaxNum) {
                    s.delete(selectionStart - 1, selectionEnd)
                    val tempSelection = selectionEnd
                    editText.text = s
                    editText.setSelection(tempSelection)
                    ToastUtils.showInfo("最多输入500字")
                }
            }
        })
    }

    fun subMitAdv(content: String) {
        MineApi.feedBack(content, UserInfoSp.getUserPhone()?.toLong()!!, 0, "") {
            onSuccess {
                if (mView.isActive()) {

                    ToastUtils.showSuccess(it.msg)
                    mView.pop()
                }
            }
            onFailed {
                ExceptionDialog.showExpireDialog(mView.requireContext(), it)
            }
        }
    }

    /**
     * 获取余额去判断是否被顶下去
     */
    fun getMoney() {
        MineApi.getUserBalance {
            onSuccess { }
            onFailed { ExceptionDialog.showExpireDialog(mView.requireContext(), it) }
        }
    }
}