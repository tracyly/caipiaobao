package com.fenghuang.caipiaobao.ui.mine

import ExceptionDialog.showExpireDialog
import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineDataBean
import com.fenghuang.caipiaobao.utils.UserInfoSp

class MineAdapter(context: Context) : BaseRecyclerAdapter<MineDataBean>(context) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineDataBean> {
        return MineItemHolder(parent)
    }

    inner class MineItemHolder(parent: ViewGroup) : BaseViewHolder<MineDataBean>(getContext(), parent, R.layout.holder_mine_item) {
        override fun onBindData(data: MineDataBean) {
            setText(R.id.tvRecordName, data.title)
            setImageResource(findView(R.id.ivRecordLogo), data.image)
        }

        override fun onItemClick(data: MineDataBean) {
            if (UserInfoSp.getIsLogin()) {
                when (data.title) {
                    "意见反馈" -> startFragment(MineFeedBackFragment())
                    "打赏记录" -> startFragment(MineRewardRecordFragment())
                    "联系客服" -> startFragment(MineContactCustomerFragment())
                    "个人资料" -> startFragment(MinePersonalFragment())
                    "我的关注" -> startFragment(MineMyAttentionFragment())
                    "设置" -> startFragment(MineSettingFragment())
                }
            } else {
                getContext()?.let { showExpireDialog(it) }
            }
        }

    }
}