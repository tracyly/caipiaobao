package com.fenghuang.caipiaobao.ui.lottery

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotterySettingChooseDataBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/4- 15:04
 * @ Describe 彩种设置选项
 *
 */

class LotterySettingChoose(context: Context) : BaseRecyclerAdapter<LotterySettingChooseDataBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LotterySettingChooseDataBean> {
        return LotterySettingChooseHolder(parent)
    }

    inner class LotterySettingChooseHolder(parent: ViewGroup) : BaseViewHolder<LotterySettingChooseDataBean>(getContext(), parent, R.layout.holder_lottery_setting_choose) {
        override fun onBindData(data: LotterySettingChooseDataBean) {
            setText(R.id.tvLotterySetting, data.des)
            setImageResource(findView(R.id.imgLotterySetting), data.image)
        }

    }


}