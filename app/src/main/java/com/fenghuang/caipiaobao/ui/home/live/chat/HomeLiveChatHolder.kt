package com.fenghuang.caipiaobao.ui.home.live.chat

import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.multitype.MultiTypeViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.function.isNotEmpty
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatBean

/**
 *  author : Peter
 *  date   : 2019/8/29 17:38
 */
class HomeLiveChatHolder : MultiTypeViewHolder<HomeLiveChatBean, HomeLiveChatHolder.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveChatBean>(context, parent, R.layout.holder_live_chat) {
        override fun onBindData(data: HomeLiveChatBean) {

            setText(R.id.tvLiveChatVip, data.room_id)
            setText(R.id.tvLiveChatUserName, data.userName)
            if (isNotEmpty(data.text)) setText(R.id.tvLiveChatContent, data.text)
        }

    }
}