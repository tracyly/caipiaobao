package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.utils.TimeUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.mine.data.MineRewardRecordResponse

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 15:55
 * @ Describe 打赏记录
 *
 */

class MineRewardRecordAdapter(context: Context, val rewardRecordPresenter: MineRewardRecordPresenter) : BaseRecyclerAdapter<MineRewardRecordResponse>(context) {


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineRewardRecordResponse> {
        return MineRewardRecordHolder(parent)
    }

    inner class MineRewardRecordHolder(parent: ViewGroup) : BaseViewHolder<MineRewardRecordResponse>(getContext(), parent, R.layout.holder_mine_reward) {
        override fun onBindData(data: MineRewardRecordResponse) {
            ImageManager.loadRoundLogo(data.avatar, findView(R.id.imgRewardPhoto))
            setText(R.id.tvRewardName, data.nickname)
            setText(R.id.tvRewardTime, TimeUtils.longToDateString(data.create_time))
            setText(R.id.tvGiftName, data.giftname)
            setText(R.id.tvGiftNum, data.gift_num.toString())
            findView<RelativeLayout>(R.id.btnDelete).setOnClickListener {
                rewardRecordPresenter.deleteRewardRecord(data.id)
                remove(getDataPosition())
            }
        }


    }


}