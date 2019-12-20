package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.view.ViewGroup
import android.widget.CheckBox
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.mine.data.MineSaveBank
import com.fenghuang.caipiaobao.ui.mine.data.MineUserBankList
import com.hwangjr.rxbus.RxBus


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/13- 13:44
 * @ Describe
 *
 */

class MineUserBankCardListAdapter(context: Context) : BaseRecyclerAdapter<MineUserBankList>(context) {


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineUserBankList> {
        return MineBankCardListHolder(parent)
    }

    inner class MineBankCardListHolder(parent: ViewGroup) : BaseViewHolder<MineUserBankList>(getContext(), parent, R.layout.holder_mine_bank_card_list) {
        override fun onBindData(data: MineUserBankList) {
            setText(R.id.tvBankName, data.bank_name)
            setText(R.id.tvBankCode, "尾号" + data.card_num.substring(data.card_num.length - 4, data.card_num.length) + "储蓄卡")
            ImageManager.loadImage(data.bank_img, findView(R.id.imgBank))
            findView<CheckBox>(R.id.cbCard).isChecked = false
            if (clickPosition != -1) {
                if (clickPosition == getDataPosition()) {
                    findView<CheckBox>(R.id.cbCard).isChecked = true
                    RxBus.get().post(MineSaveBank(data))
                }
            }
        }

        override fun onItemClick(data: MineUserBankList) {
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