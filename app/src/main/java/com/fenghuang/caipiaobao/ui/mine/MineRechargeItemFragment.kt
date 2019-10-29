package com.fenghuang.caipiaobao.ui.mine

import ExceptionDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghuang.baselib.base.fragment.BaseContentFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import kotlinx.android.synthetic.main.fragment_mine_charge_item.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/10- 13:38
 * @ Describe
 *
 */

class MineRechargeItemFragment : BaseContentFragment() {

    override fun getContentResID() = R.layout.fragment_mine_charge_item


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }


    override fun initData() {
        getPayTypeList()
    }


    private fun getPayTypeList() {
        MineApi.getPayTypeList {
            onSuccess {
                val mineRechargeItemAdapter = MineRechargeItemAdapter(getPageActivity())
                mineRechargeItemAdapter.addAll(it)
                rvRecharge.adapter = mineRechargeItemAdapter
                val value = object : LinearLayoutManager(context) {
                    override fun canScrollVertically(): Boolean {
                        return true
                    }
                }
                rvRecharge.layoutManager = value
            }
            onFailed {
                ExceptionDialog.showExpireDialog(getPageActivity(), it)
            }
        }
    }

}