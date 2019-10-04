package com.fenghuang.caipiaobao.ui.mine

import android.view.View
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/9/30- 12:51
 * @ Describe 我的界面
 *
 */

class MineFragment : BaseFragment() {

    private var mScrollY = 0

    override fun getLayoutResID(): Int {
        return R.layout.fragment_mine
    }


    override fun initData() {
        loadRootFragment(R.id.listItem, MineItemFragment())
    }


    override fun initView() {
        smartRefreshLayout.setEnableRefresh(false)
        smartRefreshLayout.setEnableOverScrollBounce(true)
        smartRefreshLayout.setEnableOverScrollDrag(true)
        smartRefreshLayout.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                iv_header.translationY = (offset - mScrollY).toFloat()
            }

            override fun onFooterMoving(footer: RefreshFooter?, isDragging: Boolean, percent: Float, offset: Int, footerHeight: Int, maxDragHeight: Int) {
                iv_header.translationY = (mScrollY - offset).toFloat()
            }
        })
    }

    override fun initEvent() {
        tvLogin.setOnClickListener {
            noLogin.visibility = View.GONE
            isLogin.visibility = View.VISIBLE
            userInfoItemLogin.visibility = View.GONE
            userInfoItem.visibility = View.VISIBLE
            ImageManager.loadRoundFrameUserLogo("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=26&gp=0.jpg", findView(R.id.userPhoto), getColor(R.color.white))
        }

    }

}