package com.fenghuang.caipiaobao.ui.home.live

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRoomRewardBean

/**
 *  author : Peter
 *  date   : 2019/10/8 13:31
 *  desc   :
 */
class HomeLiveRoomTopAdapter(context: Context) : BaseRecyclerAdapter<HomeLiveRoomRewardBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveRoomRewardBean> {
        return LiveRoomTopHolder(parent)
    }

    inner class LiveRoomTopHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveRoomRewardBean>(getContext(), parent, R.layout.holder_home_live_room_top) {
        override fun onBindData(data: HomeLiveRoomRewardBean) {
            ImageManager.loadRoundLogo(data.avatar, findView(R.id.ivUserLogo))
        }
    }
}