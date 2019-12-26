package com.fenghuang.caipiaobao.ui.mine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.fenghuang.baselib.base.adapter.BaseFragmentPageAdapter
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant.MINE_INDEX
import com.fenghuang.caipiaobao.constant.IntentConstant.MINE_USER_BALANCE
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.ui.mine.data.MineUpDateMoney
import com.fenghuang.caipiaobao.ui.mine.data.MineUpDateUser
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog.showExpireDialog
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_mine_recharge.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 12:43
 * @ Describe 充值提现
 *
 */

class MineRechargeFragment : BaseFragment() {

    override fun getLayoutResID() = R.layout.fragment_mine_recharge

    override fun isRegisterRxBus() = true

    override fun initView() {
        tvCountBalance.text = arguments?.getString(MINE_USER_BALANCE)
    }
    override fun initData() {
        upDateBalance()
        rechargeTabView.setTabText("充值", "提现")
        rechargeTabView.setOnSelectListener {
            viewPager.currentItem = it
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                rechargeTabView.setTabSelect(position)
            }
        })
        rechargeTabView.setTabSelect(arguments?.getInt(MINE_INDEX) ?: 0)

    }

    private fun setFragmentViewPager() {
        val fragments = arrayListOf<BaseFragment>(
                MineRechargeItemFragment(),
                MineRechargeCashOutFragment(tvCountBalance.text.toString())
        )
        val adapter = BaseFragmentPageAdapter(childFragmentManager, fragments)
        viewPager.adapter = adapter
        viewPager.currentItem = arguments?.getInt(MINE_INDEX) ?: 0
        viewPager.offscreenPageLimit = fragments.size
    }


    override fun initEvent() {
        imgGoBack.setOnClickListener {
            getPageActivity().onBackPressedSupport()
        }
    }

    companion object {
        fun newInstance(balance: String, index: Int): MineRechargeFragment {
            val fragment = MineRechargeFragment()
            val mBundle = Bundle()
            mBundle.putString(MINE_USER_BALANCE, balance)
            mBundle.putInt(MINE_INDEX, index)
            fragment.arguments = mBundle
            return fragment
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun upDateUserBalance(eventBean: MineUpDateUser) {
        if (eventBean.upDateMoney) upDateUserBalance()
    }

    @SuppressLint("SetTextI18n")
    private fun upDateBalance() {
        MineApi.getUserBalance {
            onSuccess {
                tvCountBalance.text = it.balance.toString()
                setFragmentViewPager()
            }
            onFailed {
                showExpireDialog(getPageActivity(), it)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun upDateUserBalance() {
        MineApi.getUserBalance {
            onSuccess {
                tvCountBalance.text = it.balance.toString()
                RxBus.get().post(MineUpDateMoney(it.balance.toString()))
            }
            onFailed {
                showExpireDialog(getPageActivity(), it)
            }
        }
    }

}