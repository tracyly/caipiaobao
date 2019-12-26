package com.fenghuang.caipiaobao.ui.mine

import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineRewardRecordResponse
import kotlinx.android.synthetic.main.fragment_mine_reward.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 15:55
 * @ Describe 打赏记录Adapter
 *
 */

class MineRewardRecordFragment : BaseMvpFragment<MineRewardRecordPresenter>() {

    var mPage = 0

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineRewardRecordPresenter()

    override fun getContentResID() = R.layout.fragment_mine_reward

    override fun getPageTitle() = getString(R.string.mine_reward_record)

    override fun isShowBackIconWhite() = false

    override fun isOverridePage() = false

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initData() {
        mPresenter.getMoney()
        mPresenter.getRewordRecord(mPage)
        rewardSmartRefresh?.setOnRefreshListener {
            mPage = 0
            mPresenter.getRewordRecord(mPage)
        }
        rewardSmartRefresh?.setOnLoadMoreListener {

            mPresenter.getRewordRecordMore(mPage)
        }

    }

    /**
     *  更新打赏记录
     */
    var mineRewardRecordAdapter: MineRewardRecordAdapter? = null
    fun upDateRewardRecord(data: List<MineRewardRecordResponse>) {
//        smartRefreshLayout.finishRefresh()
        mineRewardRecordAdapter = MineRewardRecordAdapter(getPageActivity(), mPresenter)
        mineRewardRecordAdapter?.addAll(data)
        rewardRecycle.adapter = mineRewardRecordAdapter
        val value = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
        rewardRecycle.layoutManager = value

    }


}