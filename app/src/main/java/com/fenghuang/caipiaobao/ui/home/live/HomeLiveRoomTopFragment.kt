package com.fenghuang.caipiaobao.ui.home.live

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerFragment
import com.fenghuang.baselib.base.recycler.decorate.HorizontalItemSpaceDecoration
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRoomTitleBean
import kotlinx.android.synthetic.main.fragment_home_live_room_top.*

/**
 *  author : Peter
 *  date   : 2019/10/8 11:30
 *  desc   :直播间聊天室的top栏 展示的是用户头像信息
 */
class HomeLiveRoomTopFragment : BaseRecyclerFragment<HomeLiveRoomTopPresenter, HomeLiveRoomTitleBean>() {

    override fun isEnableLoadMore() = false
    override fun isEnableRefresh() = false
    override fun getContentResID() = R.layout.fragment_home_live_room_top

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeLiveRoomTopPresenter()

    override fun attachAdapter() = HomeLiveRoomTopAdapter(getPageActivity())

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return layoutManager
    }

    override fun getItemDivider(): RecyclerView.ItemDecoration? {
        return HorizontalItemSpaceDecoration(ViewUtils.dp2px(10))
    }

    override fun initData() {
        super.initData()
        mPresenter.getAnchorInfo(ivAnchorLogo, tvTopUserName, tvTopPeople)
    }
}