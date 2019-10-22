package com.fenghuang.caipiaobao.ui.lottery

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryCodeHistoryResponse
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryOpenCodeDataBean

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
            val codeResults = arrayListOf<LotteryOpenCodeDataBean>()
            for (i in data.code.split(",")) {
                codeResults.add(LotteryOpenCodeDataBean(i))
            }
//            initLotteryOpenCode(findView(R.id.rvHistoryOpenCode), codeResults)
        }


    }

//    private fun initLotteryOpenCode(recyclerView: RecyclerView, data: List<LotteryOpenCodeDataBean>?) {
//        val value = LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
//        val lotteryOpenCodeAdapter = LotteryOpenCodeAdapter(getContext())
//        lotteryOpenCodeAdapter.addAll(data)
//        recyclerView.adapter = lotteryOpenCodeAdapter
//        recyclerView.layoutManager = value
//    }
}