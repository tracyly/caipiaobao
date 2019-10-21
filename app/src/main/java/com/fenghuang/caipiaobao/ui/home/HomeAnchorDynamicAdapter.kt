package com.fenghuang.caipiaobao.ui.home

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.decorate.GridItemSpaceDecoration
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveAnchorDynamicBean

/**
 *  author : Peter
 *  date   : 2019/10/21 13:31
 *  desc   : 主播信息动态页适配器
 */
class HomeAnchorDynamicAdapter(context: Context) : BaseRecyclerAdapter<HomeLiveAnchorDynamicBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveAnchorDynamicBean> {
        return LiveRoomTopHolder(parent)
    }

    inner class LiveRoomTopHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveAnchorDynamicBean>(getContext(), parent, R.layout.holder_anchor_dynamic) {

        lateinit var mAdapter: AnchorDynamicImageAdapter
        override fun onBindView(context: Context?) {
            context?.apply {
                val recyclerView = findView<RecyclerView>(R.id.recyclerView)
                mAdapter = AnchorDynamicImageAdapter(context)
                recyclerView.adapter = mAdapter
                recyclerView.layoutManager = GridLayoutManager(context, 3)
                recyclerView.addItemDecoration(GridItemSpaceDecoration(3, itemSpace = ViewUtils.dp2px(6), startAndEndSpace = ViewUtils.dp2px(6)))
            }
        }

        override fun onBindData(data: HomeLiveAnchorDynamicBean) {
            setText(R.id.tvDynamicName, data.nickname)
            setText(R.id.tvDynamicTitle, data.title)
            setText(R.id.tvDynamicDate, data.created)
            setText(R.id.tvDynamicLike, data.like.toString())
            mAdapter.setData(data.images)
            val findView = findView<TextView>(R.id.tvDynamicLike)
            if (data.is_like == 1) {
                setTextColor(findView, getColor(R.color.color_FF513E))
                setImageResource(findView(R.id.ivDynamicLike), R.mipmap.ic_quiz_like_select)
            } else {
                setTextColor(findView, getColor(R.color.color_bfc8d9))
                setImageResource(findView(R.id.ivDynamicLike), R.mipmap.ic_quiz_like_normal)
            }
        }
    }

    inner class AnchorDynamicImageAdapter(context: Context) : BaseRecyclerAdapter<String>(context) {

        override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
            return AnchorDynamicImageHolder(parent)
        }

        inner class AnchorDynamicImageHolder(parent: ViewGroup) : BaseViewHolder<String>(getContext(), parent, R.layout.holder_quiz_image_item) {
            override fun onBindData(data: String) {
                ImageManager.loadQuizImageRes(data, findView(R.id.ivQuizImage))
            }
        }
    }
}