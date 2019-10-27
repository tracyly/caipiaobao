package com.fenghuang.caipiaobao.ui.mine.data

import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.api.EmptySubscriber
import com.fenghuang.caipiaobao.data.bean.BaseApiBean
import com.fenghuang.caipiaobao.utils.UserInfoSp

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
    //用户信息
    private const val USER_INFO = "/index/index"
    //修改用户信息
    private const val USER_INFO_EDIT = "/index/edit"

    /**
     * 获取用户信息
     */

    fun getUserInfo(function: ApiSubscriber<MineUserInfo>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineUserInfo>() {}
        subscriber.function()
        MineApi.getApiOther().get<MineUserInfo>(USER_INFO)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .subscribe(subscriber)
    }


    /**
     * 获取打赏记录
     */

    fun getRewardRecord(function: ApiSubscriber<List<MineRewardRecordResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineRewardRecordResponse>>() {}
        subscriber.function()
        MineApi.getApi().get<List<MineRewardRecordResponse>>(MINE_REWORD_RECORD)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", SpUtils.getInt(UserConstant.USER_ID))
                .subscribe(subscriber)
    }

    /**
     * 反馈意见
     */

    fun feedBack(text: String, phone: Long, qq: Int, email: String, function: ApiSubscriber<BaseApiBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        MineApi.getApi().get<BaseApiBean>(MINE_FEED_BACK)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", SpUtils.getInt(UserConstant.USER_ID))
                .params("text", text)
                .params("phone", phone)
                .params("qq", qq)
                .params("email", email)
                .subscribe(subscriber)
    }

    /**
     * 关注列表
     */

    fun getAttentionList(function: ApiSubscriber<List<MineAttentionResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineAttentionResponse>>() {}
        subscriber.function()
        MineApi.getApi().get<List<MineAttentionResponse>>(ATTENTION)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", SpUtils.getInt(UserConstant.USER_ID))
                .subscribe(subscriber)
    }

    /**
     * 修改个人资料
     */
    fun upLoadPersonalInfo(nickname: String, gender: Int, profile: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        MineApi.getApiOther().post<String>(USER_INFO_EDIT)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("nickname", "111")
                .params("gender", 1)
                .params("profile", "666666666666666666666")
                .subscribe(subscriber)
    }

}