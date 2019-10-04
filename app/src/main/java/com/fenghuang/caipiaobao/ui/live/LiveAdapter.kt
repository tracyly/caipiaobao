package com.fenghuang.caipiaobao.ui.live

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.decorate.GridItemSpaceDecoration
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.live.data.LiveDataBean
import com.fenghuang.caipiaobao.ui.live.data.LiveTitleDataBean
import com.pingerx.imagego.core.utils.RoundType

/**
 *  author : Peter
 *  date   : 2019/8/12 16:08
 *  desc   : 直播适配器
 */
class LiveAdapter(context: Context) : BaseRecyclerAdapter<LiveTitleDataBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LiveTitleDataBean> {
        return LiveViewHolder(parent)
    }

    inner class LiveViewHolder(parent: ViewGroup) : BaseViewHolder<LiveTitleDataBean>(getContext(), parent, R.layout.holder_live) {
        private lateinit var mAdapter: LiveItemAdapter

        override fun onBindData(data: LiveTitleDataBean) {
            if (data.title.isNotEmpty()) {
                setVisibility(R.id.rlLive, View.VISIBLE)
                setText(R.id.tvLiveTitle, data.title)
            }
            if (data.url.isNotEmpty()) {
                setVisibility(R.id.ivLiveHot, View.VISIBLE)
                ImageManager.loadLiveImageRes(data.url, findView(R.id.ivLiveHot), RoundType.TOP)
            }
            mAdapter.setData(data.resultList)
        }

        override fun onBindView(context: Context?) {
            context?.apply {
                val recyclerView = findView<RecyclerView>(R.id.recyclerView)
                mAdapter = LiveItemAdapter(context)
                recyclerView.adapter = mAdapter
                recyclerView.layoutManager = GridLayoutManager(context, 2)
                recyclerView.addItemDecoration(GridItemSpaceDecoration(2, itemSpace = ViewUtils.dp2px(10), startAndEndSpace = ViewUtils.dp2px(10)))
            }
        }
    }

    inner class LiveItemAdapter(context: Context) : BaseRecyclerAdapter<LiveDataBean>(context) {
        override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LiveDataBean> {
            return LiveItemViewHolder(parent)
        }

        inner class LiveItemViewHolder(parent: ViewGroup) : BaseViewHolder<LiveDataBean>(getContext(), parent, R.layout.holder_live_list) {
            override fun onBindData(data: LiveDataBean) {
                setText(R.id.tvLiveListTitle, data.title)
//                setText(R.id.tvLiveListTitle, data.name)
                ImageManager.loadLiveImageRes(data.url, findView(R.id.ivLiveRes), RoundType.TOP)
            }

            override fun onItemClick(data: LiveDataBean) {
                super.onItemClick(data)
                startFragment(LiveDetailsFragment())
            }
        }
    }
}