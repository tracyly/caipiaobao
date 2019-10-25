package com.fenghuang.caipiaobao.ui.home.anchor

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerFragment
import com.fenghuang.baselib.base.recycler.decorate.DividerItemDecoration
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveAnchorDynamicBean

/**
 *  author : Peter
 *  date   : 2019/10/19 16:55
 *  desc   : 主播信息动态页
 */
class HomeAnchorDynamicFragment : BaseRecyclerFragment<HomeAnchorDynamicPresenter, HomeLiveAnchorDynamicBean>() {

    override fun attachView() = mPresenter.attachView(this)
    override fun getContentResID() = R.layout.fragment_home_anchor_dynamic

    override fun attachPresenter() = HomeAnchorDynamicPresenter(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
            ?: 0)

    override fun attachAdapter() = HomeAnchorDynamicAdapter(getPageActivity())

    override fun getItemDivider(): RecyclerView.ItemDecoration? {
        return DividerItemDecoration(getColor(R.color.color_f7f7f7), ViewUtils.dp2px(10))
    }

    companion object {
        fun newInstance(anchorId: Int): HomeAnchorDynamicFragment {
            val fragment = HomeAnchorDynamicFragment()
            val bundle = Bundle()
            bundle.putInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, anchorId)
            fragment.arguments = bundle
            return fragment
        }
    }
}