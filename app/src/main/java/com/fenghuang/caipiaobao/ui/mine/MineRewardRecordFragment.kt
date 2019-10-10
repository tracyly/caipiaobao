package com.fenghuang.caipiaobao.ui.mine

import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineRewardRecordBean
import kotlinx.android.synthetic.main.fragment_mine_reward.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/9- 15:55
 * @ Describe 打赏记录Adapter
 *
 */

class MineRewardRecordFragment : BaseNavFragment() {

    override fun getContentResID() = R.layout.fragment_mine_reward

    override fun getPageTitle() = getString(R.string.mine_reward_record)

    override fun isShowBackIconWhite() = false

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    private val newResults = arrayListOf<MineRewardRecordBean>()
    override fun initData() {
        newResults.add(MineRewardRecordBean("个人资料", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=26&gp=0.jpg", "1", "1", "1"))
        newResults.add(MineRewardRecordBean("今晚赢球", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=26&gp=0.jpg", "1", "1", "1"))
        newResults.add(MineRewardRecordBean("今夜有钱花", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=26&gp=0.jpg", "1", "1", "1"))
        newResults.add(MineRewardRecordBean("天天有前花", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=26&gp=0.jpg", "1", "1", "1"))
        newResults.add(MineRewardRecordBean("BeRich", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=26&gp=0.jpg", "1", "1", "1"))
        val mineRewardRecordAdapter = context?.let { MineRewardRecordAdapter(it) }
        mineRewardRecordAdapter?.addAll(newResults)
        rewardRecycle.adapter = mineRewardRecordAdapter
        val value = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
        rewardRecycle.layoutManager = value
    }
}