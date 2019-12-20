package com.fenghuang.caipiaobao.ui.mine.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.utils.UserInfoSp

/**
 *
 * @ Author  QinTian
 * @ Date  2019/11/1- 16:08
 * @ Describe 充值相关接口
 *
 */

object RechargeApi : BaseApi {

    // 诚易通充值接口
    private const val INVEST_MONEY_YCT = "/api/v1/Recharge/createorder"
    // 金钻充值接口
    private const val INVEST_MONEY_JZ = "/api/v1/Recharge/GoldDeposit"
    //  Tpay充值订单
    private const val INVEST_MONEY_TPAY = "/api/v1/Recharge/TpayCreat"


    /**
     * 获取 Tpay充值订单URL
     */
    fun getToPayUrl(amount: Float, channels_id: Int, url: String, function: ApiSubscriber<MinePayUrl>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MinePayUrl>() {}
        subscriber.function()
        getApi().post<MinePayUrl>(url)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", UserInfoSp.getUserId())
                .params("amount", amount)
                .params("channels_id", channels_id)
                .params("returnurl", "11111")
                .subscribe(subscriber)
    }


    /**
     * 获取 诚易通 支付URL
     */
    fun getPayUrl(amount: Float, channels_id: Int, function: ApiSubscriber<MinePayUrl>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MinePayUrl>() {}
        subscriber.function()
        getApi().post<MinePayUrl>(INVEST_MONEY_YCT)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", UserInfoSp.getUserId())
                .params("amount", amount)
                .params("channels_id", channels_id)
                .subscribe(subscriber)
    }


}