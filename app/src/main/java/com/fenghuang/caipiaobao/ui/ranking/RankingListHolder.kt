package com.fenghuang.caipiaobao.ui.ranking

import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.multitype.MultiTypeViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.ranking.data.RankingDataBean

/**
 *  排行列表holder
 */
class RankingListHolder : MultiTypeViewHolder<RankingDataBean, RankingListHolder.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup) : BaseViewHolder<RankingDataBean>(context, parent, R.layout.holder_ranking_list) {
        override fun onBindData(data: RankingDataBean) {
            setText(R.id.tvRankingNumber, data.rankingNumber)
            setText(R.id.tvRankingName, data.name)
            setText(R.id.tvRankingID, data.id)
            setText(R.id.tvPopularity, data.popularity)
        }

    }
}