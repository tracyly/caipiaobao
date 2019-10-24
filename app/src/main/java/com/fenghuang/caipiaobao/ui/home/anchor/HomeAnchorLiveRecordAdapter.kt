package com.fenghuang.caipiaobao.ui.home.anchor

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.utils.TimeUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveAnchorLiveRecordBean

/**
 *  author : Peter
 *  date   : 2019/10/21 13:31
 *  desc   : 主播信息直播记录适配器
 */
class HomeAnchorLiveRecordAdapter(context: Context) : BaseRecyclerAdapter<HomeLiveAnchorLiveRecordBean>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveAnchorLiveRecordBean> {
        return LiveRoomGiftHolder(parent)
    }

    inner class LiveRoomGiftHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveAnchorLiveRecordBean>(getContext(), parent, R.layout.holder_anchor_live_record) {

        override fun onBindData(data: HomeLiveAnchorLiveRecordBean) {
            setText(R.id.tvAnchorRecordGameName, data.name + ":")
            setText(R.id.tvAnchorRecordStatus, data.tip)
            setText(R.id.tvAnchorRecordTime, TimeUtils.getHourMinute(data.startTime))
            setText(R.id.tvAnchorRecordDate, TimeUtils.getYearMonthDay(data.startTime))
        }
    }

}