package com.fenghuang.caipiaobao.widget.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Editable
import android.text.Selection
import android.text.Selection.getSelectionEnd
import android.text.TextWatcher
import android.view.Gravity
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.fenghuang.baselib.utils.SoftHideKeyBoardUtil
import com.fenghuang.baselib.utils.SoftInputUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.utils.ViewUtils.getString
import com.fenghuang.baselib.utils.ViewUtils.setGone
import com.fenghuang.baselib.utils.ViewUtils.setVisible
import com.fenghuang.baselib.widget.round.RoundTextView
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.function.isEmpty
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsPresenter
import kotlinx.android.synthetic.main.dialog_bottom_input.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/11/30- 17:52
 * @ Describe
 *
 */

class BottomInputDialog(context: Context, val activity: Activity, val mPresenter: HomeLiveDetailsPresenter) : Dialog(context) {

    init {
        setContentView(R.layout.dialog_bottom_input)
        initEvent()
        SoftInputUtils.showSoftInput(dialogChatEditTexts)
        window!!.setDimAmount(0f)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.BOTTOM or Gravity.BOTTOM)
        val lp = window!!.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT  // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT   // 高度
        window!!.attributes = lp
        SoftHideKeyBoardUtil().init(activity)
        dialogEmoticonKeyboards.setupWithEditText(dialogChatEditTexts)
        dialogChatEditTexts.setOnTouchListener { _, _ ->
            dialogImgEmoji.background = context.getDrawable(R.mipmap.emotion)
            setGone(dialogEmoticonKeyboards)
            SoftInputUtils.showSoftInput(dialogChatEditTexts)
            true
        }

        tvSendMessage.setOnClickListener {
            sendMessage()
        }

        dialogChatEditTexts.addTextChangedListener(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun afterTextChanged(s: Editable?) {

                if (s?.length!! > 196) {
                    ToastUtils.showNormal("最多输入200个字")
                    return
                }
                if (s.isNotEmpty()) {
                    findViewById<RoundTextView>(R.id.tvSendMessage).delegate.backgroundColor = getContext().getColor(R.color.text_red)
                } else {
                    findViewById<RoundTextView>(R.id.tvSendMessage).delegate.backgroundColor = getContext().getColor(R.color.color_AFAFAF)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var editable = dialogChatEditTexts.text
                val len = editable.length
                if (len > 196) {
                    var selEndIndex = getSelectionEnd(editable)
                    val str = editable.toString()
                    //截取新字符串
                    val newStr = str.substring(0, start)
                    dialogChatEditTexts.setText(newStr)
                    editable = dialogChatEditTexts.text
                    //新字符串的长度
                    val newLen = editable.length
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex)
                }
            }
        })
    }

    private fun initEvent() {

        dialogImgEmoji.setOnClickListener {
            toggleKeyboard()
        }
    }

    /**
     * 显示表情与隐藏表情
     */
    private fun toggleKeyboard() {
        if (dialogEmoticonKeyboards.isVisible) {
            dialogImgEmoji.setImageResource(R.mipmap.emotion)
            setGone(dialogEmoticonKeyboards)
            SoftInputUtils.showSoftInput(dialogChatEditTexts)
        } else {
            dialogImgEmoji.setImageResource(R.mipmap.keyboard)
            disMissKeyBord()
            dialogChatEditTexts.postDelayed({ setVisible(dialogEmoticonKeyboards) }, 300)
        }
    }

    override fun dismiss() {
        disMissKeyBord()
        super.dismiss()
    }

    private fun disMissKeyBord() {
        val view = currentFocus
        if (view is TextView) {
            val mInputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mInputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }
    }

    private fun sendMessage() {
        val content = dialogChatEditTexts.text.trim().toString()
        if (isEmpty(content)) {
            ToastUtils.showNormal(getString(R.string.live_chat_empty))
        } else {
            mPresenter.sendMessage(content)
            dialogChatEditTexts.setText("")
            dismiss()
        }
    }

    /***
     * 键盘收起
     */
    fun dialogInputKeyBordDown() {
        dialogChatEditTexts.postDelayed({ if (!dialogEmoticonKeyboards.isVisible) dismiss() }, 300)
    }


}