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

class LotteryHistoryOpenCodeAdapter(context: Context) : BaseRecyclerAdapter<LotteryCodeHistoryResponse>(context) {


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LotteryCodeHistoryResponse> {
        return LotteryHistoryOpenCodeHolder(parent)
    }

    inner class LotteryHistoryOpenCodeHolder(parent: ViewGroup) : BaseViewHolder<LotteryCodeHistoryResponse>(getContext(), parent, R.layout.holder_lottery_history_opencode) {
        override fun onBindData(data: LotteryCodeHistoryResponse) {
            setText(R.id.tvOpenCount, data.issue)
            setText(R.id.tvOpenTime, data.input_time)
            val codeResults = arrayListOf<String>()
            for (i in data.code.split(",")) {
                codeResults.add(i)
            }
            initLotteryOpenCode(findView(R.id.rvHistoryOpenCode), codeResults)
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