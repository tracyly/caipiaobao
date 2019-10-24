package com.fenghuang.caipiaobao.ui.home.anchor

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveAnchorGiftListBean

/**
 *  author : Peter
 *  date   : 2019/10/21 13:31
 *  desc   : 主播信息礼物适配器
 */
class HomeAnchorGiftAdapter(context: Context) : BaseRecyclerAdapter<HomeLiveAnchorGiftListBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveAnchorGiftListBean> {
        return LiveRoomGiftHolder(parent)
    }

    inner class LiveRoomGiftHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveAnchorGiftListBean>(getContext(), parent, R.layout.holder_anchor_gift) {

        override fun onBindData(data: HomeLiveAnchorGiftListBean) {
            ImageManager.loadHomeGameListLogo(data.icon, findView(R.id.ivAnchorGif))
        }
    }

}