package com.fenghuang.caipiaobao.ui.home.live

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerFragment
import com.fenghuang.baselib.base.recycler.decorate.HorizontalItemSpaceDecoration
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRoomRewardBean

/**
 *  author : Peter
 *  date   : 2019/10/8 11:30
 *  desc   :直播间聊天室的top栏 展示打赏榜logo
 */
class HomeLiveRoomTopFragment : BaseRecyclerFragment<HomeLiveRoomTopPresenter, HomeLiveRoomRewardBean>() {

    override fun isEnableLoadMore() = false
    override fun isEnableRefresh() = false

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeLiveRoomTopPresenter(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
            ?: 0)

    override fun attachAdapter() = HomeLiveRoomTopAdapter(getPageActivity())

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return layoutManager
    }

    override fun getItemDivider(): RecyclerView.ItemDecoration? {
        return HorizontalItemSpaceDecoration(ViewUtils.dp2px(10))
    }

    companion object {
        fun newInstance(anchorId: Int): HomeLiveRoomTopFragment {
            val fragment = HomeLiveRoomTopFragment()
            val bundle = Bundle()
            bundle.putInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, anchorId)
            fragment.arguments = bundle
            return fragment
        }
    }
}