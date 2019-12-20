package com.fenghuang.caipiaobao.ui.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.utils.NetWorkUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveListResponse
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsFragment
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.widget.cardview.LCardView
import com.fenghuang.caipiaobao.widget.gif.GifImageView


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/4- 13:55
 * @ Describe 开奖号码
 *
 */

class HomeMoreLiveAdapter(context: Context) : BaseRecyclerAdapter<HomeLiveListResponse>(context) {

    val TYPE_HEADER = 0  //说明是带有Header的
    val TYPE_FOOTER = 1  //说明是带有Footer的
    val TYPE_NORMAL = 2  //说明是不带有header和footer的
    //HeaderView, FooterView
    private var mHeaderView: View? = null
    private var mFooterView: View? = null

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveListResponse> {
        if (viewType === TYPE_HEADER) {
            return FooterHolder(parent)
        }
//        if (viewType === TYPE_FOOTER) {
//            return HeaderHolder(parent)
//        }
        return HomeMoreLiveHolder(parent)
    }

    inner class HomeMoreLiveHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveListResponse>(getContext(), parent, R.layout.holder_home_hot_live) {
        override fun onBindData(data: HomeLiveListResponse) {
            setText(R.id.tvHotLiveTitle, data.name)
            setText(R.id.tvHotLiveIntro, data.live_intro)
            setText(R.id.tvHotLiveName, data.nickname)
            if (data.tags.isNotEmpty()) setText(R.id.tvHotLiveTag, data.tags[0].title)
            ImageManager.loadHomeHotLive(data.avatar, findView(R.id.ivHotLiveLogo))
            if (data.tags.isNotEmpty()) ImageManager.loadHomeGameListLogo(data.tags[0].icon, findView(R.id.ivHotLiveTag))
            if (data.live_status == 1) {
                val ivHotLiveStatus = findView<GifImageView>(R.id.ivHotLiveStatus)
                ivHotLiveStatus.setGifResource(R.drawable.ic_home_live_gif)
                ivHotLiveStatus.play(-1)
                setVisibility(R.id.ivHotLiveStatus, true)
            } else {
                setVisibility(R.id.ivHotLiveStatus, false)
            }
            findView<LCardView>(R.id.cardViewItem).setOnClickListener {
                if (NetWorkUtils.isNetworkConnected()) {
                    LaunchUtils.startFragment(getContext(), HomeLiveDetailsFragment.newInstance(data.anchor_id, data.name, data.live_status, data.avatar))
                } else ToastUtils.showError("网络连接已断开")
            }
        }
    }

    inner class FooterHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveListResponse>(getContext(), parent, R.layout.recycle_foot) {
        override fun onBindData(data: HomeLiveListResponse) {
        }
    }

    inner class HeaderHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveListResponse>(getContext(), parent, R.layout.recycle_foot) {
        override fun onBindData(data: HomeLiveListResponse) {
        }
    }

    override fun getItemCount(): Int {
        return if (mHeaderView == null && mFooterView == null) {
            getAllData().size
        } else if (mHeaderView == null && mFooterView != null) {
            getAllData().size + 1
        } else if (mHeaderView != null && mFooterView == null) {
            getAllData().size + 1
        } else {
            getAllData().size + 2
        }
    }


    override fun getItemViewType(position: Int): Int {
//        if (position == 0) {
//            //第一个item应该加载Header
//            return TYPE_HEADER
//        }
        if (position == itemCount - 1) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER
        }
        return TYPE_NORMAL
    }

    /**
     * 判断是否是Header的位置
     * 如果是Header的则返回true否则返回false
     * @param position
     * @return
     */
    fun isHeader(position: Int): Boolean {
        return position in 0..0
    }


    /**
     * 判断是否是Footer的位置
     * 如果是Footer的位置则返回true否则返回false
     * @param position
     * @return
     */
    fun isFooter(position: Int): Boolean {
        return position < itemCount && position >= itemCount - 1
    }
}
