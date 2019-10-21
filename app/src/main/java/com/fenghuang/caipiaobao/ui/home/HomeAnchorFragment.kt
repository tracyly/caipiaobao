package com.fenghuang.caipiaobao.ui.home

import androidx.viewpager.widget.ViewPager
import com.fenghuang.baselib.base.adapter.BaseFragmentPageAdapter
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_home_anchor_information.*

/**
 *  author : Peter
 *  date   : 2019/10/19 16:19
 *  desc   :
 */
class HomeAnchorFragment : BaseMvpFragment<HomeAnchorPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeAnchorPresenter()

    override fun getLayoutResID() = R.layout.fragment_home_anchor_information

    override fun initContentView() {
        anchorTabView.setRankingTab()
    }

    override fun initEvent() {
        anchorTabView.setOnSelectListener {
            viewPager.currentItem = it
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                anchorTabView.setTabSelect(position)
            }
        })
    }

    override fun initData() {
        val fragments = arrayListOf<BaseFragment>(
                HomeAnchorDataFragment(),
                HomeAnchorDynamicFragment()
        )
        val adapter = BaseFragmentPageAdapter(childFragmentManager, fragments)
        viewPager.adapter = adapter
        viewPager.currentItem = 0
        viewPager.offscreenPageLimit = fragments.size
        showContent(placeholder)

    }
}