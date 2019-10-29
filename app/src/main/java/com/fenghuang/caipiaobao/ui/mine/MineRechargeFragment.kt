package com.fenghuang.caipiaobao.ui.mine

import android.os.Bundle
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant.MINE_USER_BALANCE
import kotlinx.android.synthetic.main.fragment_mine_recharge.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 12:43
 * @ Describe 充值提现
 *
 */

class MineRechargeFragment : BaseFragment() {

    override fun getLayoutResID() = R.layout.fragment_mine_recharge

    override fun initView() {
        tvCountBalance.text = arguments?.getString(MINE_USER_BALANCE)
    }



    override fun initData() {
        val fragments = arrayListOf<BaseFragment>()
        val titles = arrayListOf("充值", "提现")
//        titles.forEachIndexed { _, _ ->
//        }
        fragments.add(MineRechargeItemFragment())
        fragments.add(MineRechargeCashOutFragment())
        setTabAdapter(viewPager, tabLayout, fragments, titles)
        ViewUtils.setTabLayoutTextStyle(tabLayout)
        // 设置第一条选中
        ViewUtils.updateTabLayoutView(tabLayout.getTabAt(0), true)
        showContent(placeholder)
    }

    override fun initEvent() {
        imgGoBack.setOnClickListener {
            getPageActivity().onBackPressedSupport()
        }
    }

    companion object {
        fun newInstance(balance: String): MineRechargeFragment {
            val fragment = MineRechargeFragment()
            val mBundle = Bundle()
            mBundle.putString(MINE_USER_BALANCE, balance)
            fragment.arguments = mBundle
            return fragment
        }
    }
}