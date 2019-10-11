package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_mine_recharge.*
import kotlinx.android.synthetic.main.fragment_mine_recharge.tabLayout
import kotlinx.android.synthetic.main.fragment_mine_recharge.viewPager
import kotlinx.android.synthetic.main.fragment_ranking.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 12:43
 * @ Describe 充值提现
 *
 */

class MineRechargeFragment : BaseFragment() {

    override fun getLayoutResID() = R.layout.fragment_mine_recharge

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
}