package com.fenghuang.caipiaobao.test

import android.view.View
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : BaseNavFragment() {

    override fun getPageTitle(): String? {
        return "测试"
    }

    override fun getContentResID(): Int = R.layout.fragment_test

    override fun initContentView() {

    }

    override fun initEvent() {
        setOnClick(btnClick)
    }

    override fun onClick(view: View) {
        if (view == btnClick) {
            // TODO 跳转
            ToastUtils.showToast("点击")
        }
    }

}