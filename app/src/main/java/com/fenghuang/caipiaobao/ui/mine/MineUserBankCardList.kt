package com.fenghuang.caipiaobao.ui.mine

import android.os.Bundle
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.ui.mine.data.MineSaveBank
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_mine_bank_card_list.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/11- 17:17
 * @ Describe y用户银行卡列表
 *
 */

class MineUserBankCardList : BaseMvpFragment<MineUserBankCardListPresenter>() {


    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineUserBankCardListPresenter()

    override fun isOverridePage() = false

    override fun getContentResID() = R.layout.fragment_mine_bank_card_list

    override fun getPageTitle() = getString(R.string.mine_card_list)

    override fun isShowBackIconWhite() = false

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initData() {
        mPresenter.initList(getPageActivity(), rvCardList)
    }

    companion object {
        fun newInstance(list: String): MineUserBankCardList {
            val fragment = MineUserBankCardList()
            val bundle = Bundle()
            bundle.putString(IntentConstant.MINE_USER_BAMK_LIST, list)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun saveUserBankSelect(event: MineSaveBank) {
        UserInfoSp.putSelectBankCard(event.data)
        pop()
    }

}