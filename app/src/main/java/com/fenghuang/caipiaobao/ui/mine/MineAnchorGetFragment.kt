package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.anchor_get_fragment.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/13- 12:01
 * @ Describe
 *
 */

class MineAnchorGetFragment : BaseNavFragment() {

    override fun isShowBackIconWhite() = true

    override fun getPageTitle() = "主播招募"

    override fun getLayoutResID() = R.layout.anchor_get_fragment

    override fun isOverridePage() = false

    override fun initEvent() {
        imgBack.setOnClickListener {
            pop()
        }
    }
}