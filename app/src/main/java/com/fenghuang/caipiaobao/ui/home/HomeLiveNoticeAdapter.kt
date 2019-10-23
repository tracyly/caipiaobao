package com.fenghuang.caipiaobao.ui.home

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.utils.TimeUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.anchor.HomeAnchorFragment
import com.fenghuang.caipiaobao.ui.home.data.HomeLivePopResponse

/**
 *  author : Peter
 *  date   : 2019/10/1 13:27
 *  desc   : 直播预告
 */
class HomeLiveNoticeAdapter(context: Context) : BaseRecyclerAdapter<HomeLivePopResponse>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLivePopResponse> {
        return HomeLiveNoticeHolder(parent)
    }

    inner class HomeLiveNoticeHolder(parent: ViewGroup) : BaseViewHolder<HomeLivePopResponse>(getContext(), parent, R.layout.holder_home_live_notice) {
        override fun onBindData(data: HomeLivePopResponse) {
            when (getDataPosition()) {
                0 -> {
                    ImageManager.loadRoundFrameLogo(data.avatar, findView(R.id.ivLiveNoticeLogo), getColor(R.color.color_FFF32C))
                    setVisible(R.id.ivLiveNoticeTop)
                    setImageResource(findView(R.id.ivLiveNoticeTop), R.mipmap.ic_home_live_notice_1)
                }
                1 -> {
                    ImageManager.loadRoundFrameLogo(data.avatar, findView(R.id.ivLiveNoticeLogo), getColor(R.color.color_D6DCEF))
                    setVisible(R.id.ivLiveNoticeTop)
                    setImageResource(findView(R.id.ivLiveNoticeTop), R.mipmap.ic_home_live_notice_2)
                }
                2 -> {
                    ImageManager.loadRoundFrameLogo(data.avatar, findView(R.id.ivLiveNoticeLogo), getColor(R.color.color_C79D7B))
                    setVisible(R.id.ivLiveNoticeTop)
                    setImageResource(findView(R.id.ivLiveNoticeTop), R.mipmap.ic_home_live_notice_3)
                }
                else -> {
                    setGone(R.id.ivLiveNoticeTop)
                    ImageManager.loadRoundLogo(data.avatar, findView(R.id.ivLiveNoticeLogo))
                }
            }
            setText(R.id.tvLiveNoticeName, data.nickname)
            setText(R.id.tvLiveNoticeGameName, data.name)
            setText(R.id.tvLiveNoticeDate, TimeUtils.getHourMinute(data.starttime) + "～" + TimeUtils.getHourMinute(data.endtime))
        }

        override fun onItemClick(data: HomeLivePopResponse) {
            super.onItemClick(data)
            startFragment(HomeAnchorFragment.newInstance(data.aid))
        }
    }
}