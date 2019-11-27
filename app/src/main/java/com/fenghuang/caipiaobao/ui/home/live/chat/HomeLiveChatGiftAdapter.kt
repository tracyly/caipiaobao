package com.fenghuang.caipiaobao.ui.home.live.chat

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/11/18- 12:14
 * @ Describe
 *
 */

class HomeLiveChatGiftAdapter(context: Context) : BaseRecyclerAdapter<HomeLiveChatGifBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveChatGifBean> {
        return LiveChatGiftAdapter(parent)
    }

    inner class LiveChatGiftAdapter(parent: ViewGroup) : BaseViewHolder<HomeLiveChatGifBean>(getContext(), parent, R.layout.dialog_chat_bottom_gif_item) {
        override fun onBindData(data: HomeLiveChatGifBean) {
            ImageManager.loadImage(data.icon, findView(R.id.imgGift))
        }
    }
}