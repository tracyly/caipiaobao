package com.fenghuang.caipiaobao.ui.lottery.del

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerFragment
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryDataBean


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 17:36
 * @ Describe 彩种
 *
 */

class LotteryTypeFragment : BaseRecyclerFragment<LotteryTypePresenter, LotteryDataBean>() {

    override fun isEnableRefresh() = false

    override fun isEnableLoadMore() = false

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LotteryTypePresenter()

    override fun attachAdapter() = LotteryTypeAdapter(getPageActivity())

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return layoutManager
    }
}