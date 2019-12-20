package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.utils.LaunchUtils
import kotlinx.android.synthetic.main.gift_send_fragment.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/13- 12:01
 * @ Describe
 *
 */

class MineGiftSendFragment : BaseNavFragment() {

    override fun isShowBackIconWhite() = true

    override fun getPageTitle() = "充值送豪礼"

    override fun getLayoutResID() = R.layout.gift_send_fragment

    override fun isOverridePage() = false


    override fun initEvent() {
        tvGoToRecharge.setOnClickListener {
            LaunchUtils.startFragment(context, MineRechargeFragment.newInstance("0", 0))
        }
        imgBackSend.setOnClickListener {
            pop()
        }
    }

}