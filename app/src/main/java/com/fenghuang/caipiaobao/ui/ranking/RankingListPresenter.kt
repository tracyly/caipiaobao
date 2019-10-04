package com.fenghuang.caipiaobao.ui.ranking

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.ui.ranking.data.RanKingTopThreeBean
import com.fenghuang.caipiaobao.ui.ranking.data.RankingDataBean

class RankingListPresenter : BaseRecyclerPresenter<RankingListFragment>() {

    private val newResults = arrayListOf<RankingDataBean>()
    override fun loadData(page: Int) {
        mView.addItem(RanKingTopThreeBean("我是亚军", "1555454", "4545",
                "我是冠军", "8954541", "2124445",
                "我是季军", "4445557", "12165"))

        newResults.add(RankingDataBean("4", "张三", "1236455", "6645"))
        newResults.add(RankingDataBean("5", "李四", "1236455", "6645"))
        newResults.add(RankingDataBean("6", "王五", "1236455", "6645"))
        newResults.add(RankingDataBean("7", "赵留", "1236455", "6645"))
        newResults.add(RankingDataBean("8", "关羽", "1236455", "6645"))
        newResults.add(RankingDataBean("9", "张飞", "1236455", "6645"))
        newResults.add(RankingDataBean("10", "曹操", "1236455", "6645"))
        newResults.add(RankingDataBean("11", "刘备", "1236455", "6645"))
        newResults.add(RankingDataBean("12", "吕布", "1236455", "6645"))
        newResults.add(RankingDataBean("13", "张辽", "1236455", "6645"))
        newResults.add(RankingDataBean("14", "赵云", "1236455", "6645"))
        newResults.add(RankingDataBean("15", "马超", "1236455", "6645"))
        newResults.add(RankingDataBean("16", "貂蝉", "1236455", "6645"))
        newResults.add(RankingDataBean("17", "王昭君", "1236455", "6645"))
        newResults.add(RankingDataBean("18", "安其拉", "1236455", "6645"))
        mView.addAll(newResults)
    }


}