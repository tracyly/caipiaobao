package com.fenghuang.caipiaobao.ui.lottery.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/21- 19:12
 * @ Describe
 *
 */

object LotteryApi : BaseApi {


    private const val LOTTERY_TYPE = "/idx/sort"

    private const val LOTTERY_NEW_CODE = "/idx/indexNewOne"

    private const val LOTTERY_HISTORY_CODE = "/idx/history"

    private const val LOTTERY_EXPERT_PLAN = "/plan/index"
    /**
     * 获取彩种
     */

    fun getLotteryType(function: ApiSubscriber<List<LotteryTypeResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<LotteryTypeResponse>>() {}
        subscriber.function()
        LotteryApi.getAipOpenUrl()
                .post<List<LotteryTypeResponse>>(LOTTERY_TYPE)
                .subscribe(subscriber)
    }

    /**
     * 获取的最新开奖号
     */

    fun getLotteryNewCode(lotteryId: Int, function: ApiSubscriber<LotteryCodeNewResponse>.() -> Unit) {
        val subscriber = object : ApiSubscriber<LotteryCodeNewResponse>() {}
        subscriber.function()
        LotteryApi.getAipOpenUrl()
                .post<LotteryCodeNewResponse>(LOTTERY_NEW_CODE)
                .params("lottery_id", lotteryId)
                .subscribe(subscriber)
    }

    /**
     * 获取历史开奖号
     */

    fun getLotteryHistoryCode(lotteryId: Int, date: String, function: ApiSubscriber<List<LotteryCodeHistoryResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<LotteryCodeHistoryResponse>>() {}
        subscriber.function()
        LotteryApi.getAipOpenUrl()
                .post<List<LotteryCodeHistoryResponse>>(LOTTERY_HISTORY_CODE)
                .params("lottery_id", lotteryId)
                .params("belong_date", date)
                .subscribe(subscriber)
    }


    /**
     * 获取专家计划
     */

    fun getExpertPlan(lottery_id: Int, issue: String, function: ApiSubscriber<List<LotteryExpertPlanResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<LotteryExpertPlanResponse>>() {}
        subscriber.function()
        LotteryApi.getApiOther()
                .get<List<LotteryExpertPlanResponse>>(LOTTERY_EXPERT_PLAN)
                .params("lottery_id", lottery_id)
                .params("issue", issue)
                .subscribe(subscriber)
    }

}