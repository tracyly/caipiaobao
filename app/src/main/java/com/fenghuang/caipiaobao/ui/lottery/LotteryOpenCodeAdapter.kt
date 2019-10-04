package com.fenghuang.caipiaobao.ui.lottery

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryOpenCodeDataBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/4- 13:55
 * @ Describe 开奖号码
 *
 */

class LotteryOpenCodeAdapter(context: Context) : BaseRecyclerAdapter<LotteryOpenCodeDataBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LotteryOpenCodeDataBean> {
        return LotteryTypeHolder(parent)
    }

    inner class LotteryTypeHolder(parent: ViewGroup) : BaseViewHolder<LotteryOpenCodeDataBean>(getContext(), parent, R.layout.holder_lottery_opencode) {
        override fun onBindData(data: LotteryOpenCodeDataBean) {
            setText(R.id.tvOpenCode, data.code)
        }

    }


}

