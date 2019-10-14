package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.view.ViewGroup
import android.widget.CheckBox
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineBankCardBean


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/13- 13:44
 * @ Describe
 *
 */

class MineBankCardListAdapter(context: Context) : BaseRecyclerAdapter<MineBankCardBean>(context) {


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineBankCardBean> {
        return MineBankCardListHolder(parent)
    }

    inner class MineBankCardListHolder(parent: ViewGroup) : BaseViewHolder<MineBankCardBean>(getContext(), parent, R.layout.holder_mine_bank_card_list) {
        override fun onBindData(data: MineBankCardBean) {
            setText(R.id.tvBankName, data.bankName)
            setText(R.id.tvBankCode, "尾号" + data.bankCode.substring(data.bankCode.length - 4, data.bankCode.length) + "储蓄号")
            when {
                data.bankType == "1" -> setImageResource(findView(R.id.imgBank), R.mipmap.ic_mine_zggs)
                data.bankType == "2" -> setImageResource(findView(R.id.imgBank), R.mipmap.ic_mine_jsyh)
                data.bankType == "3" -> setImageResource(findView(R.id.imgBank), R.mipmap.ic_mine_shpf)
                data.bankType == "4" -> setImageResource(findView(R.id.imgBank), R.mipmap.ic_mine_zsyh)
            }
            findView<CheckBox>(R.id.cbCard).isChecked = false
            if (clickPosition != -1) {
                if (clickPosition == getDataPosition()) {
                    findView<CheckBox>(R.id.cbCard).isChecked = true
                }
            }
        }

        override fun onItemClick(data: MineBankCardBean) {
            singleChoose(getDataPosition())
        }
    }

    //这个是checkbox的Hashmap集合
    private var clickPosition: Int = -1

    /**
     * 单选
     *
     * @param position
     */
    fun singleChoose(position: Int) {
        clickPosition = position
        notifyDataSetChanged()
    }
}