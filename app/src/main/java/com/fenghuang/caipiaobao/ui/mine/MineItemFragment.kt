package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.recycler.BaseRecyclerFragment
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.mine.data.MineDataBean

class MineItemFragment : BaseRecyclerFragment<MineItemPresenter, MineDataBean>() {


    override fun isEnableRefresh() = false
    override fun isEnableLoadMore() = false

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineItemPresenter(SpUtils.getBoolean(UserConstant.USER_LOGIN, UserConstant.USER_IS_LOGIN))

    override fun attachAdapter() = MineItemAdapter(getPageActivity())

    override fun isStatusBarForegroundBlack() = true

}