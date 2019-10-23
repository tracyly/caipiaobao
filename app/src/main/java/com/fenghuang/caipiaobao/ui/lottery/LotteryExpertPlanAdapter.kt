package com.fenghuang.caipiaobao.ui.lottery

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryExpertPlanResponse

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/23- 12:03
 * @ Describe
 *
 */

class LotteryExpertPlanAdapter(context: Context) : BaseRecyclerAdapter<LotteryExpertPlanResponse>(context) {

    val codeResults = arrayListOf<String>()
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LotteryExpertPlanResponse> {
        return LotteryExpertPlanHolder(parent)
    }


    inner class LotteryExpertPlanHolder(parent: ViewGroup) : BaseViewHolder<LotteryExpertPlanResponse>(getContext(), parent, R.layout.holder_lottery_expert_plan) {
        override fun onBindData(data: LotteryExpertPlanResponse) {

            ImageManager.loadRoundLogo(data.avatar, findView(R.id.imExpertPhoto))
            setText(R.id.tvExpertName, data.nickname)
            setText(R.id.tvExpertIssue, data.issue + "期")
            setText(R.id.tvRate, (data.hit_rate.toDouble() * 100).toString())
            for (i in data.code.split(",")) {
                codeResults.add(i)
            }
            initLotteryOpenCode(findView(R.id.rvExpert), codeResults)
        }

    }

    private fun initLotteryOpenCode(recyclerView: RecyclerView, data: List<String>?) {
        val value = LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        val lotteryOpenCodeAdapter = LotteryOpenCodeAdapter(getContext())
        lotteryOpenCodeAdapter.addAll(data)
        recyclerView.adapter = lotteryOpenCodeAdapter
        recyclerView.layoutManager = value
    }
}