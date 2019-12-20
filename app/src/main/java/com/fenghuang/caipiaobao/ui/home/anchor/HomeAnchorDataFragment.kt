package com.fenghuang.caipiaobao.ui.home.anchor

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
    private lateinit var mHomeAnchorTagAdapter: HomeAnchorTagAdapter
    private lateinit var mHomeAnchorGiftAdapter: HomeAnchorGiftAdapter
    private lateinit var mHomeAnchorLiveRecordAdapter: HomeAnchorLiveRecordAdapter
    override fun getContentResID() = R.layout.fragment_home_anchor_data

    override fun initContentView() {
        super.initContentView()
        initAnchorTagAdapter()
        initAnchorGiftAdapter()
        initAnchorLiveRecordAdapter()
    }

    override fun initData() {
        val data = arguments?.getSerializable(IntentConstant.HOME_LIVE_ANCHOR_DATA) as HomeLiveAnchorInfoBean
        if (data.lottery.isNotEmpty()) {
            val sb = StringBuffer()
            data.lottery.forEach {
                sb.append(it.name + "   ")
            }
            setText(R.id.tvAnchorGame, sb.toString())
        }
        if (data.tagList.isNotEmpty()) mHomeAnchorTagAdapter.addAll(data.tagList)
        if (data.giftList.isNotEmpty())
            mHomeAnchorGiftAdapter.addAll(data.giftList)
        else {
            setGone(anchorGiftRecyclerView)
            setVisible(tvNotReceiveGift)
        }
        setText(R.id.tvAnchorDate, data.duration.toString())
        setText(R.id.tvAnchorOpenDate, TimeUtils.longToDateString(data.liveStartTime) + "-" + TimeUtils.longToDateString(data.liveEndTime))
        setText(R.id.anchorGiftNumber, "共 " + data.giftNum.toString() + " 件")
        if (data.live_record.isNotEmpty())
            mHomeAnchorLiveRecordAdapter.addAll(data.live_record)
        else {
            setGone(liveRecordingRecycler)
            setVisible(tvNotLiveReceive)
        }
    }

    private fun initAnchorLiveRecordAdapter() {
        mHomeAnchorLiveRecordAdapter = HomeAnchorLiveRecordAdapter(getPageActivity())
        liveRecordingRecycler.adapter = mHomeAnchorLiveRecordAdapter
        val linearLayoutManager = LinearLayoutManager(getPageActivity())
        liveRecordingRecycler.layoutManager = linearLayoutManager
    }

    private fun initAnchorGiftAdapter() {
        mHomeAnchorGiftAdapter = HomeAnchorGiftAdapter(getPageActivity())
        anchorGiftRecyclerView.adapter = mHomeAnchorGiftAdapter
        val linearLayoutManager = LinearLayoutManager(getPageActivity())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        anchorGiftRecyclerView.layoutManager = linearLayoutManager
    }

    private fun initAnchorTagAdapter() {
        mHomeAnchorTagAdapter = HomeAnchorTagAdapter(getPageActivity())
        anchorTagRecyclerView.adapter = mHomeAnchorTagAdapter
        val linearLayoutManager = LinearLayoutManager(getPageActivity())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        anchorTagRecyclerView.layoutManager = linearLayoutManager
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