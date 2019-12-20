package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.text.TextUtils
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.mine.data.MinePayTypeList
import com.fenghuang.caipiaobao.widget.dialog.InvestDialog

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 14:34
 * @ Describe 充值Adapter
 *
 */

class MineRechargeItemAdapter(context: Context) : BaseRecyclerAdapter<MinePayTypeList>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MinePayTypeList> {
        return MineRechargeItemHolder(parent)
    }

    inner class MineRechargeItemHolder(parent: ViewGroup) : BaseViewHolder<MinePayTypeList>(getContext(), parent, R.layout.holder_mine_recharge_item) {
        override fun onBindData(data: MinePayTypeList) {
            setText(R.id.tvBankName, data.channels_type)
            setText(R.id.tvMoneyBorder, "(" + (if ((data.low_money + "") == "null") "" else data.low_money) + " ~ " + (if ((data.low_money + "") == "null") "" else data.high_money) + ")")
            ImageManager.loadPayTypeListLogo(data.icon.replace("\\", "/"), findView(R.id.imgBankType))

        }

        override fun onItemClick(data: MinePayTypeList) {
            val dialog = getContext()?.let { InvestDialog(it, data.channels_type, "确定", "取消") }
            dialog?.setConfirmClickListener {
                judgeMoney(dialog, data)
            }
            dialog?.show()
        }

        private fun judgeMoney(dialog: InvestDialog, it: MinePayTypeList) {
            if (!TextUtils.isEmpty(dialog.getText())) {
                val money = dialog.getText().toDouble()
                if (it.high_money.toDouble() >= money && it.low_money.toDouble() <= money) {
                    startFragment(MineInvestFragment.newInstance(money, it.id, it.apiroute))
                    dialog.dismiss()
                } else ToastUtils.showInfo("充值金额为:" + it.low_money + "~" + it.high_money)

            } else ToastUtils.showInfo("充值金额为:" + it.low_money + "~" + it.high_money)
        }
    }


}