package com.fenghuang.caipiaobao.ui.mine

import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.CloseMore
import com.fenghuang.caipiaobao.ui.mine.data.MineAttentionResponse
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_mine_attention.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/17- 16:00
 * @ Describe 我的关注
 *
 */

class MineMyAttentionFragment : BaseMvpFragment<MineMyAttentionPresenter>() {


    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineMyAttentionPresenter()

    override fun getPageTitle() = getString(R.string.mine_attention)

    override fun isShowBackIconWhite() = false

    override fun getContentResID() = R.layout.fragment_mine_attention

    override fun isOverridePage() = false

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)

    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initData() {
        mPresenter.getMoney()
        mPresenter.getAttentionList()
        smartRefreshAttention.setEnablePureScrollMode(true)
        smartRefreshAttention.setEnableOverScrollDrag(true)
    }

    var mineMyAttentionAdapter: MineMyAttentionAdapter? = null
    fun upDateAttentionList(list: List<MineAttentionResponse>) {
        mineMyAttentionAdapter = MineMyAttentionAdapter(getPageActivity(), mPresenter)
        mineMyAttentionAdapter?.addAll(list)
        attentionRecycle.adapter = mineMyAttentionAdapter
        val value = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
        attentionRecycle.layoutManager = value

//
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun CloseMore(eventBean: CloseMore) {
        pop()
    }
}