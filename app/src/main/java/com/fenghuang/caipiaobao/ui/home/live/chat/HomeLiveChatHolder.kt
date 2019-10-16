package com.fenghuang.caipiaobao.ui.home.live.chat

import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.multitype.MultiTypeViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.function.isNotEmpty
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatBean
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRedMessageBean
import com.hwangjr.rxbus.RxBus

/**
 *  author : Peter
 *  date   : 2019/8/29 17:38
 */
class HomeLiveChatHolder : MultiTypeViewHolder<HomeLiveChatBean, HomeLiveChatHolder.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveChatBean>(context, parent, R.layout.holder_live_chat) {
        override fun onBindData(data: HomeLiveChatBean) {
            if (data.type == "subscribe") {
                setText(R.id.tvLiveChatContent, getString(R.string.live_chat_hint_room))
            } else {
                if (isNotEmpty(data.text)) setText(R.id.tvLiveChatContent, data.text)
            }

            if (data.gift_type == 4) {
                // 红包
                RxBus.get().post(HomeLiveRedMessageBean(4))
            }
            setText(R.id.tvLiveChatUserName, data.userName)
            setText(R.id.tvLiveChatVip, "V" + data.vip.toString())
        }

    }
}