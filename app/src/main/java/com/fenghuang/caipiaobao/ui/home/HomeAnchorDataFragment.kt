package com.fenghuang.caipiaobao.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghuang.baselib.base.fragment.BaseContentFragment
import com.fenghuang.baselib.utils.TimeUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveAnchorInfoBean
import kotlinx.android.synthetic.main.fragment_home_anchor_data.*

/**
 *  author : Peter
 *  date   : 2019/10/19 16:55
 *  desc   :主播信息资料页
 */
class HomeAnchorDataFragment : BaseContentFragment() {

    override fun getContentResID() = R.layout.fragment_home_anchor_data


    override fun initData() {
        val data = arguments?.getSerializable(IntentConstant.HOME_LIVE_ANCHOR_DATA) as HomeLiveAnchorInfoBean
        if (data.lottery.isNotEmpty()) {
            var sb = StringBuffer()
            data.lottery.forEach {
                sb.append(it.name + "   ")
            }
            setText(R.id.tvAnchorGame, sb.toString())
        }
        if (data.tagList.isNotEmpty()) {
            val homeAnchorTagAdapter = HomeAnchorTagAdapter(getPageActivity())
            homeAnchorTagAdapter.addAll(data.tagList)
            anchorTagRecyclerView.adapter = homeAnchorTagAdapter
            val linearLayoutManager = LinearLayoutManager(getPageActivity())
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            anchorTagRecyclerView.layoutManager = linearLayoutManager
        }

        setText(R.id.tvAnchorDate, data.duration.toString())
        setText(R.id.tvAnchorOpenDate, TimeUtils.longToDateString(data.liveStartTime) + "-" + TimeUtils.longToDateString(data.liveEndTime))
    }

    companion object {
        fun newInstance(data: HomeLiveAnchorInfoBean): HomeAnchorDataFragment {
            val fragment = HomeAnchorDataFragment()
            val bundle = Bundle()
            bundle.putSerializable(IntentConstant.HOME_LIVE_ANCHOR_DATA, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}