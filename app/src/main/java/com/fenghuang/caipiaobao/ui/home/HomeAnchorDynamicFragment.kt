package com.fenghuang.caipiaobao.ui.home

import com.fenghuang.baselib.base.recycler.BaseRecyclerFragment
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveAnchorDynamicBean

/**
 *  author : Peter
 *  date   : 2019/10/19 16:55
 *  desc   : 主播信息动态页
 */
class HomeAnchorDynamicFragment : BaseRecyclerFragment<HomeAnchorDynamicPresenter, HomeLiveAnchorDynamicBean>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeAnchorDynamicPresenter()

    override fun attachAdapter() = HomeAnchorDynamicAdapter(getPageActivity())
}