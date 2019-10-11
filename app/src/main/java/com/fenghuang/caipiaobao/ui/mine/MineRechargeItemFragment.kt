package com.fenghuang.caipiaobao.ui.mine

import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghuang.baselib.base.fragment.BaseContentFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineRechargeBean
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


    private val newResults = arrayListOf<MineRechargeBean>()
    override fun initData() {
        newResults.add(MineRechargeBean("银联快捷", R.mipmap.ic_mine_yinlian))
        newResults.add(MineRechargeBean("云闪付扫码", R.mipmap.ic_mine_yunshanfu))
        newResults.add(MineRechargeBean("微信扫码", R.mipmap.ic_mine_wx))
        newResults.add(MineRechargeBean("支付宝扫码", R.mipmap.ic_mine_alipay))
        newResults.add(MineRechargeBean("银行卡转账", R.mipmap.ic_mine_yhk))
        newResults.add(MineRechargeBean("网银在线", R.mipmap.ic_mine_wangyinzaixian))
        val mineRechargeItemAdapter = context?.let { MineRechargeItemAdapter(it) }
        mineRechargeItemAdapter?.addAll(newResults)
        rvRecharge.adapter = mineRechargeItemAdapter
        val value = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
        rvRecharge.layoutManager = value
    }
}