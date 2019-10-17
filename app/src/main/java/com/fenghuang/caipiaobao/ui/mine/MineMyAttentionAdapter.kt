package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.mine.data.MineAttentionResponse

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 15:55
 * @ Describe 打赏记录
 *
 */

class MineMyAttentionAdapter(context: Context) : BaseRecyclerAdapter<MineAttentionResponse>(context) {


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineAttentionResponse> {
        return MineMyAttentionHolder(parent)
    }

    inner class MineMyAttentionHolder(parent: ViewGroup) : BaseViewHolder<MineAttentionResponse>(getContext(), parent, R.layout.holder_attention) {
        override fun onBindData(data: MineAttentionResponse) {
            ImageManager.loadRoundLogo(data.avatar, findView(R.id.imgAttPhoto))
            setText(R.id.tvAttName, data.nickname)
            setText(R.id.tvAttDes, data.intro)
            if (data.livestatus == "1") setVisibility(R.id.imgIsLive, true)
            findView<RelativeLayout>(R.id.btnDelete).setOnClickListener {
                remove(getDataPosition())
            }
        }
    }


}