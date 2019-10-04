package com.fenghuang.caipiaobao.ui.lottery.del

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 17:38
 * @ Describe
 *
 */
import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryDataBean

class LotteryTypeAdapter(context: Context) : BaseRecyclerAdapter<LotteryDataBean>(context) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LotteryDataBean> {
        return LotteryTypeItemHolder(parent)
    }

    inner class LotteryTypeItemHolder(parent: ViewGroup) : BaseViewHolder<LotteryDataBean>(getContext(), parent, R.layout.holder_mine_lottery_type) {
        override fun onBindData(data: LotteryDataBean) {
            setImageResource(findView(R.id.imgLottery), data.image)
            setText(R.id.tvLottery, data.title)
        }

        override fun onItemClick(data: LotteryDataBean) {
            when (data.title) {
//                "我的消息" -> startFragment(MineMessageFragment())
//                "打赏记录" -> startFragment(MineRewardRecordFragment())
//                "意见反馈" -> startFragment(MineUserFeedBackFragment())
            }
        }


    }

}