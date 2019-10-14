package com.fenghuang.caipiaobao.ui.mine.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.ui.home.data.HomeApi

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 15:33
 * @ Describe
 *
 */
object MineApi : BaseApi {
    private const val MINE_REWORD_RECOED = "/index.php/api/v1/user/user_reward_list/"

    /**
     * 获取打赏记录
     */

    fun getRewardRecord(function: ApiSubscriber<List<MineRewardRecordResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineRewardRecordResponse>>() {}
        subscriber.function()
        HomeApi.getApi().get<List<MineRewardRecordResponse>>(MINE_REWORD_RECOED)
                .params("user_id", 1)
                .subscribe(subscriber)

    }

}