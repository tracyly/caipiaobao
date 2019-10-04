package com.fenghuang.caipiaobao.ui.lottery.del

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryDataBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 17:38
 * @ Describe
 *
 */

class LotteryTypePresenter : BaseRecyclerPresenter<LotteryTypeFragment>() {
    private val newResults = arrayListOf<LotteryDataBean>()
    override fun loadData(page: Int) {
        newResults.add(LotteryDataBean("幸运飞艇", R.mipmap.logo))
        newResults.add(LotteryDataBean("欢乐赛车", R.mipmap.logo))
        newResults.add(LotteryDataBean("福彩3D", R.mipmap.logo))
        newResults.add(LotteryDataBean("江苏快三", R.mipmap.logo))
        newResults.add(LotteryDataBean("重庆时时彩", R.mipmap.logo))
        mView.showDatas(newResults)

    }


}