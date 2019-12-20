package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineDataBean
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog.showExpireDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp


class MineAdapter(context: Context) : BaseRecyclerAdapter<MineDataBean>(context) {

    private var isLive: String = ""

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MineDataBean> {
        return MineItemHolder(parent)
    }

    inner class MineItemHolder(parent: ViewGroup) : BaseViewHolder<MineDataBean>(getContext(), parent, R.layout.holder_mine_item) {
        override fun onBindData(data: MineDataBean) {
            setText(R.id.tvRecordName, data.title)
            setImageResource(findView(R.id.ivRecordLogo), data.image)
            if (getItemData(getDataPosition()).title == "我的关注") {
                findView<TextView>(R.id.tvIsAnchorLive).text = isLive
            }
            if (UserInfoSp.getIsLogin() && getItemData(getDataPosition()).title == "设置") {
                setVisibilityItem(true, itemView)
            }
            if (!UserInfoSp.getIsLogin() && getItemData(getDataPosition()).title == "设置") {
                setVisibilityItem(false, itemView)
            }
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

    fun notifyIsLive(string: String) {
        this.isLive = string
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setVisibilityItem(isVisible: Boolean, itemView: View) {
        val param = itemView.layoutParams as RecyclerView.LayoutParams
        if (isVisible) {
            param.height = ViewUtils.dp2px(50)
            param.width = LinearLayout.LayoutParams.MATCH_PARENT
            itemView.visibility = View.VISIBLE
        } else {
            itemView.visibility = View.GONE
            param.height = 0
            param.width = 0
        }
        itemView.layoutParams = param
    }

}