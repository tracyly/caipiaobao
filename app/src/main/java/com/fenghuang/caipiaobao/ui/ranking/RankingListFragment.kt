package com.fenghuang.caipiaobao.ui.ranking

import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseMultiRecyclerFragment
import com.fenghuang.baselib.base.recycler.decorate.DividerItemDecoration
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.ranking.data.RanKingTopThreeBean
import com.fenghuang.caipiaobao.ui.ranking.data.RankingDataBean
import kotlinx.android.synthetic.main.fragment_ranking_list.*

/**
 *  排行列表
 */
class RankingListFragment : BaseMultiRecyclerFragment<RankingListPresenter>() {


    override fun getContentResID() = R.layout.fragment_ranking_list

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = RankingListPresenter()

    override fun initPageView() {
        rankingListTabView.setRankingTab()
        register(RanKingTopThreeBean::class.java, RankingTopThreeHolder())
        register(RankingDataBean::class.java, RankingListHolder())
    }

    override fun getItemDivider(): RecyclerView.ItemDecoration? {
        return DividerItemDecoration(getColor(R.color.color_f2f2f6), ViewUtils.dp2px(1))
    }
}