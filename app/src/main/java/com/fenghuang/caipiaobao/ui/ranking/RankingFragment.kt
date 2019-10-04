package com.fenghuang.caipiaobao.ui.ranking

import com.fenghuang.baselib.base.fragment.BaseContentFragment
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 *  排行
 */
class RankingFragment : BaseContentFragment() {

    override fun getContentResID() = R.layout.fragment_ranking

    override fun initContentView() {
        showLoading(placeholder)
    }

    override fun initData() {
        val fragments = arrayListOf<BaseFragment>()
        val titles = arrayListOf("主播人气", "打赏榜", "预测榜", "连赢榜")
        titles.forEachIndexed { _, _ ->
            fragments.add(RankingListFragment())
        }
        setTabAdapter(viewPager, tabLayout, fragments, titles)
        ViewUtils.setTabLayoutTextStyle(tabLayout)
        // 设置第一条选中
        ViewUtils.updateTabLayoutView(tabLayout.getTabAt(0), true)
        showContent(placeholder)
    }

}