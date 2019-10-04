package com.fenghuang.caipiaobao.ui.ranking

import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.multitype.MultiTypeViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.ranking.data.RanKingTopThreeBean


/**
 *    author : Peter
 *    date   : 2019/7/3013:39
 *    desc   : 排行榜前三甲holder
 */
class RankingTopThreeHolder : MultiTypeViewHolder<RanKingTopThreeBean, RankingTopThreeHolder.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup) : BaseViewHolder<RanKingTopThreeBean>(context, parent, R.layout.holder_ranking_top_three) {
        override fun onBindData(data: RanKingTopThreeBean) {
            setText(R.id.tvRankingChampionName, data.championName)
            setText(R.id.tvRankingChampionID, data.championID)
            setText(R.id.tvRankingChampionPopularity, " ${data.championPopularity}")

            setText(R.id.tvRankingRunnerUpName, data.runnerUpName)
            setText(R.id.tvRankingRunnerUpID, data.runnerUpID)
            setText(R.id.tvRankingRunnerUpPopularity, " ${data.runnerUpPopularity}")

            setText(R.id.tvRankingThirdPlaceName, data.thirdPlaceName)
            setText(R.id.tvRankingThirdPlaceID, data.thirdPlaceID)
            setText(R.id.tvRankingThirdPlacePopularity, " ${data.thirdPlacePopularity}")

//            ImageManager.loadRoundLogo("https://c-ssl.duitang.com/uploads/item/201810/08/20181008130033_bfvzg.thumb.700_0.jpeg", findView(R.id.ivRankingRunnerUpLogo))
//            ImageManager.loadRoundLogo("https://c-ssl.duitang.com/uploads/item/201708/26/20170826165627_fcxVE.jpeg", findView(R.id.ivRankingChampionLogo))
//            ImageManager.loadRoundLogo("https://c-ssl.duitang.com/uploads/item/201503/31/20150331155006_kd5rr.jpeg", findView(R.id.ivRankingThirdPlaceLogo))
        }
    }
}