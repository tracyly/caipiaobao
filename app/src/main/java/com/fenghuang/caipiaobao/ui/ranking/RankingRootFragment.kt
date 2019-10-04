package com.fenghuang.caipiaobao.ui.ranking

import com.fenghuang.baselib.base.adapter.BaseFragmentPageAdapter
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_ranking_root.*

/**
 * 排行榜入口页
 */
class RankingRootFragment : BaseNavFragment() {

    // tab类型
    companion object {
        const val TYPE_ANCHOR = 1    // 主播
        const val TYPE_EXPERT = 2  // 专家
    }

    override fun getPageTitle() = getString(R.string.tab_quiz)

    override fun getContentResID() = R.layout.fragment_ranking_root

    override fun isShowBackIcon() = false

    override fun initContentView() {
        rankingTabView.setRankingTab()
    }

    override fun initData() {
        val fragments = arrayListOf<BaseFragment>(
                RankingFragment(),
                RankingFragment()
        )
        val adapter = BaseFragmentPageAdapter(childFragmentManager, fragments)
        viewPager.adapter = adapter
        viewPager.currentItem = 0
        viewPager.offscreenPageLimit = fragments.size
        showContent(placeholder)
    }

    override fun initEvent() {
        rankingTabView.setOnSelectListener {
            if (it == 0) {
                // 主播
                viewPager.currentItem = 0
            } else {
                // 专家
                viewPager.currentItem = 1
            }
        }

    }

//    override fun onCreateFragmentAnimator(): FragmentAnimator {
//        return FragmentFlipAnimator()
//    }

}