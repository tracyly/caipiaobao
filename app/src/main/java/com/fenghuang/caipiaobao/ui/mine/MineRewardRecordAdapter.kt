package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.mine.data.MineRewardRecordBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 15:55
 * @ Describe 打赏记录
 *
 */

class MineRewardRecordAdapter(context: Context) : BaseRecyclerAdapter<MineRewardRecordBean>(context) {


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineRewardRecordBean> {
        return MineRewardRecordHolder(parent)
    }

    inner class MineRewardRecordHolder(parent: ViewGroup) : BaseViewHolder<MineRewardRecordBean>(getContext(), parent, R.layout.holder_mine_reward) {
        override fun onBindData(data: MineRewardRecordBean) {
            setText(R.id.tvRewardName, data.name)
            ImageManager.loadRoundLogo(data.image, findView(R.id.imgRewardPhoto))
        }

    }


}