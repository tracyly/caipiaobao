package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineRechargeBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 14:34
 * @ Describe 充值Adapter
 *
 */

class MineRechargeItemAdapter(context: Context) : BaseRecyclerAdapter<MineRechargeBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineRechargeBean> {
        return MineRechargeItemHolder(parent)
    }

    inner class MineRechargeItemHolder(parent: ViewGroup) : BaseViewHolder<MineRechargeBean>(getContext(), parent, R.layout.holder_mine_recharge_item) {
        override fun onBindData(data: MineRechargeBean) {
            setText(R.id.tvBankName, data.nameBank)
            setImageResource(findView(R.id.imgBankType), data.imageBank)
        }

    }

}