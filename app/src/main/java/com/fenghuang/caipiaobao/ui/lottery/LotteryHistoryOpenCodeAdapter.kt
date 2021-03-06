package com.fenghuang.caipiaobao.ui.lottery

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryCodeHistoryResponse

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/21- 15:42
 * @ Describe
 *
 */

class LotteryHistoryOpenCodeAdapter(context: Context, var lotteryId: Int) : BaseRecyclerAdapter<LotteryCodeHistoryResponse>(context) {

    val codeResults = arrayListOf<String>()

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LotteryCodeHistoryResponse> {
        return LotteryHistoryOpenCodeHolder(parent)
    }

    inner class LotteryHistoryOpenCodeHolder(parent: ViewGroup) : BaseViewHolder<LotteryCodeHistoryResponse>(getContext(), parent, R.layout.holder_lottery_history_opencode) {
        override fun onBindData(data: LotteryCodeHistoryResponse) {
            setText(R.id.tvOpenCount, data.issue + "期")
            setText(R.id.tvOpenTime, data.input_time)
            codeResults.clear()
            for (i in data.code.split(",")) {
                codeResults.add(i)
            }
            if (codeResults.size == 7) {
                codeResults.add(6, "+")
                initLotteryHongKongOpenCode(findView(R.id.rvHistoryOpenCode), codeResults)
            } else initLotteryOpenCode(findView(R.id.rvHistoryOpenCode), codeResults)
        }
    }

    private fun initLotteryOpenCode(recyclerView: RecyclerView, data: List<String>?) {
        val value = LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        val lotteryOpenCodeAdapter = LotteryOpenCodeAdapter(getContext())
        lotteryOpenCodeAdapter.addAll(data)
        recyclerView.adapter = lotteryOpenCodeAdapter
        recyclerView.layoutManager = value
    }

    //香港彩适配器
    private fun initLotteryHongKongOpenCode(recyclerView: RecyclerView, data: List<String>?) {
        val value = LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        val lotteryOpenCodeHongKongAdapter = LotteryOpenCodeHongKongAdapter(getContext())
        lotteryOpenCodeHongKongAdapter.addAll(data)
        recyclerView.adapter = lotteryOpenCodeHongKongAdapter
        recyclerView.layoutManager = value
    }

}