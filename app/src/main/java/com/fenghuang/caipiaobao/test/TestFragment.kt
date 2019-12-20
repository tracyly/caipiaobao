package com.fenghuang.caipiaobao.test


import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.caipiaobao.R

class TestFragment : BaseNavFragment() {

    override fun getPageTitle(): String? {
        return "测试"
    }

    override fun getContentResID(): Int = R.layout.fragment_test

    override fun initContentView() {
        val et_inputMessage = findView(R.id.et_inputMessage) as EditText
        val iv_more = findView(R.id.iv_more) as ImageView
        val ll_rootEmojiPanel = findView(R.id.ll_rootEmojiPanel) as LinearLayout
    }

    override fun initData() {

    }

    override fun initEvent() {

    }


}