package com.fenghuang.caipiaobao.ui.home.live.liveroom

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/11/25- 17:28
 * @ Describe
 *
 */

class HomeLiveRecycleGiftAdapter(context: Context) : BaseRecyclerAdapter<HomeLiveChatGifBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveChatGifBean> {
        return LiveRoomGiftHolder(parent)
    }

    inner class LiveRoomGiftHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveChatGifBean>(getContext(), parent, R.layout.item_home_entrance) {
        override fun onBindData(data: HomeLiveChatGifBean) {
//            ImageManager.loadRoundLogo(data.avatar, findView(R.id.ivUserLogo))
            setText(R.id.entrance_name, data.name)
        }
    }

}