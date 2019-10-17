package com.fenghuang.caipiaobao.ui.mine.data

import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.bean.BaseApiBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 15:33
 * @ Describe
 *
 */
object MineApi : BaseApi {
    //打赏记录
    private const val MINE_REWORD_RECORD = "/index.php/api/v1/user/user_reward_list/"
    //意见反馈
    private const val MINE_FEED_BACK = "/index.php/api/v1/user/user_feedback/"
    //关注
    private const val ATTENTION = "/index.php/api/v1/user/User_follow_list/"

    /**
     * 获取打赏记录
     */

    fun getRewardRecord(function: ApiSubscriber<List<MineRewardRecordResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineRewardRecordResponse>>() {}
        subscriber.function()
        MineApi.getApi().get<List<MineRewardRecordResponse>>(MINE_REWORD_RECORD)
                .headers("token", SpUtils.getString("token"))
                .params("user_id", 1)
                .subscribe(subscriber)
    }

    /**
     * 反馈意见
     */

    fun feedBack(user_id: Int, type: Int, text: String, phone: Long, qq: Int, email: String, function: ApiSubscriber<BaseApiBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        MineApi.getApi().get<BaseApiBean>(MINE_FEED_BACK)
                .headers("token", SpUtils.getString("token"))
                .params("user_id", user_id)
                .params("type", type)
                .params("text", text)
                .params("phone", phone)
                .params("qq", qq)
                .params("email", email)
                .subscribe(subscriber)
    }

    /**
     * 反馈意见
     */

    fun getAttentionList(user_id: Int, function: ApiSubscriber<List<MineAttentionResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineAttentionResponse>>() {}
        subscriber.function()
        MineApi.getApi().get<List<MineAttentionResponse>>(ATTENTION)
                .headers("token", SpUtils.getString("token"))
                .params("user_id", user_id)
                .subscribe(subscriber)
    }

}