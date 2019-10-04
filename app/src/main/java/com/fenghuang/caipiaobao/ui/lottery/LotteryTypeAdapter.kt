package com.fenghuang.caipiaobao.ui.lottery

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryDataBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/4- 13:55
 * @ Describe 彩种
 *
 */

class LotteryTypeAdapter(context: Context) : BaseRecyclerAdapter<LotteryDataBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LotteryDataBean> {
        return LotteryTypeHolder(parent)
    }

    inner class LotteryTypeHolder(parent: ViewGroup) : BaseViewHolder<LotteryDataBean>(getContext(), parent, R.layout.holder_lottery_type_item) {
        override fun onBindData(data: LotteryDataBean) {
            setText(R.id.tvLotteryType, data.title)
            setImageResource(findView(R.id.imgLotteryType), data.image)
        }

    }


}

