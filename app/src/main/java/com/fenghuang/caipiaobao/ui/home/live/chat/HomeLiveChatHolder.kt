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
            if (data.type == "subscribe") {
                setText(R.id.tvLiveChatContent, getString(R.string.live_chat_hint_room))
            } else {
                if (data.gift_type == 4) {
                    // 红包
                    setText(R.id.tvLiveChatContent, "在直播间发送了红包")
                } else {
                    if (isNotEmpty(data.text)) setText(R.id.tvLiveChatContent, data.text)
                }
            }
            when (data.vip) {
                1 -> setImageResource(findView(R.id.ivLiveVip), R.mipmap.ic_live_chat_vip1)
                2 -> setImageResource(findView(R.id.ivLiveVip), R.mipmap.ic_live_chat_vip2)
                3 -> setImageResource(findView(R.id.ivLiveVip), R.mipmap.ic_live_chat_vip3)
                4 -> setImageResource(findView(R.id.ivLiveVip), R.mipmap.ic_live_chat_vip4)
                5 -> setImageResource(findView(R.id.ivLiveVip), R.mipmap.ic_live_chat_vip5)
                6 -> setImageResource(findView(R.id.ivLiveVip), R.mipmap.ic_live_chat_vip6)
            }
            setText(R.id.tvLiveChatUserName, data.userName)
//            setText(R.id.tvLiveChatVip, "V" + data.vip.toString())
        }

    }
}