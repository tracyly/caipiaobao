package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.MineBankCardBean
import com.fenghuang.caipiaobao.utils.JsonUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/11- 16:02
 * @ Describe
 *
 */

class MineRechargeCashOutPresenter : BaseMvpPresenter<MineRechargeCashOutFragment>() {

    fun getBankList(): Array<MineBankCardBean> {
        //判断是否有银行卡
        val bankCard = arrayListOf<MineBankCardBean>()
        bankCard.add(MineBankCardBean("1", "1111111111111111111", "中国银行"))
        bankCard.add(MineBankCardBean("2", "2222222222222222222", "中国建设银行"))
        bankCard.add(MineBankCardBean("3", "3333333333333333333", "上海浦发银行"))
        bankCard.add(MineBankCardBean("4", "4444444444444444444", "招商银行"))
        UserInfoSp.putUserBank(JsonUtils.toJson(bankCard))
        return JsonUtils.fromJson(UserInfoSp.getUserBank().toString(), Array<MineBankCardBean>::class.java)
    }

}