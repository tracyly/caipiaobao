package com.fenghuang.caipiaobao.ui.mine.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.bean.BaseApiBean
import com.fenghuang.caipiaobao.ui.home.data.HomeApi

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 15:33
 * @ Describe
 *
 */
object MineApi : BaseApi {
    private const val MINE_REWORD_RECORD = "/index.php/api/v1/user/user_reward_list/"

    private const val MINE_FEED_BACK = "/index.php/api/v1/user/user_feedback/"

    /**
     * 获取打赏记录
     */

    fun getRewardRecord(function: ApiSubscriber<List<MineRewardRecordResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineRewardRecordResponse>>() {}
        subscriber.function()
        HomeApi.getApi().get<List<MineRewardRecordResponse>>(MINE_REWORD_RECORD)
                .params("user_id", 1)
                .subscribe(subscriber)
    }

    /**
     * 反馈意见
     */

    fun feedBack(user_id: Int, type: Int, text: String, phone: Long, qq: Int, email: String, function: ApiSubscriber<BaseApiBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        HomeApi.getApi().get<BaseApiBean>(MINE_FEED_BACK)
                .params("user_id", user_id)
                .params("type", type)
                .params("text", text)
                .params("phone", phone)
                .params("qq", qq)
                .params("email", email)
                .subscribe(subscriber)
    }

}