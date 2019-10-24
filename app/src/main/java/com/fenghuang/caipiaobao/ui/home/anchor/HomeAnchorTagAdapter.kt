package com.fenghuang.caipiaobao.ui.home.anchor

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveAnchorTagListBean

/**
 *  author : Peter
 *  date   : 2019/10/21 13:31
 *  desc   : 主播信息标签适配器
 */
class HomeAnchorTagAdapter(context: Context) : BaseRecyclerAdapter<HomeLiveAnchorTagListBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveAnchorTagListBean> {
        return LiveRoomTopHolder(parent)
    }

    inner class LiveRoomTopHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveAnchorTagListBean>(getContext(), parent, R.layout.holder_anchor_tag) {

        override fun onBindData(data: HomeLiveAnchorTagListBean) {
            setText(R.id.tvAnchorTag, data.title)
            ImageManager.loadHomeGameListLogo(data.icon, findView(R.id.ivAnchorTag))
        }
    }

}