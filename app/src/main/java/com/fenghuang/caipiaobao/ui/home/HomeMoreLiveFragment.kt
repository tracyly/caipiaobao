package com.fenghuang.caipiaobao.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.base.recycler.decorate.GridItemSpaceDecoration
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveListResponse
import kotlinx.android.synthetic.main.fragment_live_more.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/10- 15:43
 * @ Describe
 *
 */
class HomeMoreLiveFragment : BaseMvpFragment<HomeMoreLivePresenter>() {

    var mPage = 0

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeMoreLivePresenter()

    override fun isOverridePage() = false

    override fun getPageTitle() = "全部主播"

    override fun getContentResID() = R.layout.fragment_live_more

    override fun isShowBackIconWhite() = false

    override fun initContentView() {
        super.initContentView()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun initData() {
        if (arguments?.get(IntentConstant.HOME_LIVE_WHICH) == 1) {
            mPresenter.getHotLive(mPage)
        } else mPresenter.getExpert(mPage)

        moreSmartRefreshLayout.setOnLoadMoreListener {
            mPage++
            if (arguments?.get(IntentConstant.HOME_LIVE_WHICH) == 1) {
                mPresenter.getHotLive(mPage)
            } else mPresenter.getExpert(mPage)
        }

    }

    override fun initEvent() {
        moreSmartRefreshLayout.setOnRefreshListener {
            mPage = 0
            mPresenter.getHotLive(mPage)
        }
    }

    fun updateHotLive(data: List<HomeLiveListResponse>) {
        val adapter = HomeMoreLiveAdapter(getPageActivity())
        rvLiveMore.adapter = adapter
        adapter.addAll(data)
        val gridLayoutManager = object : GridLayoutManager(context, 2) {
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
//        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
//                return if ( adapter.isFooter(position)) gridLayoutManager.spanCount else 1
//            }
//
//        }
        rvLiveMore.layoutManager = gridLayoutManager
        if (rvLiveMore.itemDecorationCount == 0) {
            rvLiveMore.addItemDecoration(GridItemSpaceDecoration(2, itemSpace = ViewUtils.dp2px(10), startAndEndSpace = ViewUtils.dp2px(6)))
        }
    }


    companion object {
        fun newInstance(which: Int): HomeMoreLiveFragment {
            val fragment = HomeMoreLiveFragment()
            val bundle = Bundle()
            bundle.putInt(IntentConstant.HOME_LIVE_WHICH, which)
            fragment.arguments = bundle
            return fragment
        }
    }
}