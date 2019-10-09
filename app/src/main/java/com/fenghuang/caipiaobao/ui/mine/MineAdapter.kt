package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineDataBean

class MineAdapter(context: Context) : BaseRecyclerAdapter<MineDataBean>(context) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineDataBean> {
        return MineItemHolder(parent)
    }

    inner class MineItemHolder(parent: ViewGroup) : BaseViewHolder<MineDataBean>(getContext(), parent, R.layout.holder_mine_item) {
        override fun onBindData(data: MineDataBean) {
            setText(R.id.tvRecordName, data.title)
            setImageResource(findView(R.id.ivRecordLogo), data.image)
        }

        override fun onItemClick(data: MineDataBean) {
            when (data.title) {
                "意见反馈" -> {
                    startFragment(MineFeedBackFragment())
                }

            }
        }


    }

}