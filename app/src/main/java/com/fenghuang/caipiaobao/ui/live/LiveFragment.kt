package com.fenghuang.caipiaobao.ui.live

import com.fenghuang.baselib.base.recycler.BaseRecyclerNavFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.live.data.LiveTitleDataBean

/**
 *  author : Peter
 *  date   : 2019/8/10 18:08
 *  desc   : 直播列表，展示所有直播的内容
 */
class LiveFragment : BaseRecyclerNavFragment<LivePresenter, LiveTitleDataBean>() {


    override fun getPageTitle() = "直播聊天"

    override fun isShowBackIcon() = false

    override fun getContentResID() = R.layout.fragment_live

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LivePresenter()

    override fun attachAdapter() = LiveAdapter(getPageActivity())
}