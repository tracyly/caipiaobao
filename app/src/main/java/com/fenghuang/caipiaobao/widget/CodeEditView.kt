package com.fenghuang.caipiaobao.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import java.util.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/8/25- 15:29
 * @ Describe 密码输入框
 *
 */

class CodeEditView : LinearLayout, TextWatcher, View.OnClickListener, View.OnLongClickListener {
    private var editViewNum = 6 //默认输入框数量
    private val mTextViewsList = ArrayList<TextView>() //存储EditText对象
    private var mContext: Context? = null
    private var mEditText: EditText? = null
    private var borderSize = 35 //方格边框大小
    private var borderMargin = 10 //方格间距
    private var textSize = 8 //字体大小
    private var textColor = 0xff //字体颜色
    private val inputType = InputType.TYPE_CLASS_NUMBER
    private var callBack: InputEndListener? = null

    val text: String
        get() = mEditText!!.text.toString()


    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        initData(context, attrs)
        init(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initData(context, attrs)
        init(context)
    }

    @SuppressLint("Recycle")
    private fun initData(context: Context, attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.CodeEditView)
        borderSize = array.getInteger(R.styleable.CodeEditView_borderSize, 35)
        borderMargin = array.getInteger(R.styleable.CodeEditView_borderMargin, 10)
        textSize = array.getInteger(R.styleable.CodeEditView_textSize, 8)
        textColor = array.getColor(R.styleable.CodeEditView_textColor, Color.BLACK)
        editViewNum = array.getInteger(R.styleable.CodeEditView_borderNum, 6)
    }

    fun setOnInputEndCallBack(onInputEndCallBack: InputEndListener) {
        callBack = onInputEndCallBack
    }


    override fun onLongClick(v: View): Boolean {
        return true
    }


    interface InputEndListener {
        fun input(text: String)

        fun afterTextChanged(text: String)
    }

    private fun init(context: Context) {
        mContext = context
        initEditText(context)
        val params = LayoutParams(
                ViewUtils.dp2px(borderSize), ViewUtils.dp2px(borderSize))
        params.setMargins(ViewUtils.dp2px(borderMargin), 0, 0, 0)
        for (i in 0 until editViewNum) {
            val textView = TextView(mContext)
            textView.setBackgroundResource(R.drawable.shape_border_normal)
            textView.gravity = Gravity.CENTER
            textView.textSize = ViewUtils.sp2px(textSize).toFloat()
            textView.paint.isFakeBoldText = true
            textView.layoutParams = params
            textView.inputType = inputType
            textView.setTextColor(textColor)
            textView.setOnClickListener(this)
            textView.setOnLongClickListener(this)
            mTextViewsList.add(textView)
            addView(textView)
        }

        android.os.Handler().postDelayed({
            mEditText!!.isFocusable = true
            mEditText!!.isFocusableInTouchMode = true
            mEditText!!.requestFocus()
            val imm = mContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }, 500)

        mEditText!!.setOnKeyListener(OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (mEditText!!.text.length >= mTextViewsList.size) return@OnKeyListener false
                mTextViewsList[mEditText!!.text.length].text = ""
            }
            false
        })

        this.setOnLongClickListener(this)
    }

    private fun initEditText(context: Context) {
        mEditText = EditText(context)
        mEditText!!.setBackgroundColor(Color.parseColor("#00000000"))
        mEditText!!.maxLines = 1
        mEditText!!.inputType = inputType
        mEditText!!.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(editViewNum))
        mEditText!!.addTextChangedListener(this)
        mEditText!!.textSize = 0f
        mEditText!!.height = 1
        mEditText!!.width = 1
        addView(mEditText)
    }

    //清空文字
    fun clearText() {
        mEditText!!.setText("")
        for (textView in mTextViewsList) {
            textView.text = ""
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (callBack != null) {
            callBack!!.afterTextChanged(s.toString())
        }
        if (s.length <= 1) {
            mTextViewsList[0].text = s
        } else {
            mTextViewsList[mEditText!!.text.length - 1].text = s.subSequence(s.length - 1, s.length)
        }
        if (s.length == editViewNum) {
            if (callBack != null) {
                callBack!!.input(mEditText!!.text.toString())
            }
        }
    }

    override fun onClick(v: View) {
        mEditText!!.isFocusable = true
        mEditText!!.isFocusableInTouchMode = true
        mEditText!!.requestFocus()
        val imm = mContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

}