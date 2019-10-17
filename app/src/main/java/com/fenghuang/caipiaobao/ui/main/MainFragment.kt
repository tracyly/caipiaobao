package com.fenghuang.caipiaobao.ui.main

import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.base.fragment.BasePageFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.bet.BetFragment
import com.fenghuang.caipiaobao.ui.home.HomeFragment
import com.fenghuang.caipiaobao.ui.home.data.HomeClickMine
import com.fenghuang.caipiaobao.ui.lottery.LotteryFragment
import com.fenghuang.caipiaobao.ui.mine.MineFragment
import com.fenghuang.caipiaobao.ui.quiz.QuizFragment
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.activity_main.*


class MainFragment : BasePageFragment() {

    private val mFragments = arrayListOf<BaseFragment>()

    override fun getLayoutResID() = R.layout.activity_main
    override fun isRegisterRxBus() = true

    override fun initView() {
        mFragments.add(HomeFragment())
        mFragments.add(LotteryFragment())
        mFragments.add(BetFragment())
        mFragments.add(QuizFragment())
        mFragments.add(MineFragment())
        loadMultipleRootFragment(R.id.mainContainer, 0,
                mFragments[0], mFragments[1], mFragments[2], mFragments[3], mFragments[4])
    }

    override fun initEvent() {
        tabHome.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
            tabHome.isChecked = true
            tabLive.isChecked = false
            tabRanking.isChecked = false
            tabMine.isChecked = false
            tabBetting.isFocusable = false
            showHideFragment(mFragments[0])
        }
        tabLive.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
            tabHome.isChecked = false
            tabRanking.isChecked = false
            tabMine.isChecked = false
            tabLive.isChecked = true
            tabBetting.isFocusable = false
            showHideFragment(mFragments[1])
        }
        tabBetting.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
            tabHome.isChecked = false
            tabRanking.isChecked = false
            tabLive.isChecked = false
            tabMine.isChecked = false
            tabBetting.isChecked = true
            showHideFragment(mFragments[2])
        }
        tabRanking.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
            tabRanking.isChecked = true
            tabHome.isChecked = false
            tabLive.isChecked = false
            tabMine.isChecked = false
            tabBetting.isFocusable = false
            showHideFragment(mFragments[3])
        }
        tabMine.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
            tabMine.isChecked = true
            tabHome.isChecked = false
            tabLive.isChecked = false
            tabRanking.isChecked = false
            tabBetting.isFocusable = false
            showHideFragment(mFragments[4])
        }
    }

    /**
     * 接收Home头像点击事件
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onClickMine(clickMine: HomeClickMine) {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
        tabMine.isChecked = true
        tabHome.isChecked = false
        tabLive.isChecked = false
        tabRanking.isChecked = false
        tabBetting.isFocusable = false
        showHideFragment(mFragments[4])
    }
}