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
    //用户等级
    private const val USER_LEVEL = "/api/v1/user/vip_now/"
    //修改用户信息
    private const val USER_INFO_EDIT = "/index/edit"
    //兑换钻石
    private const val USER_CHANGE_DIAMOND = "/api/v1/user/exchange_diamond/"
    //获取钻石
    private const val USER_DIAMOND = "/api/v1/user/diamond_now/"
    //获取余额
    private const val USER_BALANCE = "/index/balance"
    //获取支付列表
    private const val PAY_TYPE_LIST = "/api/v1/Recharge/getList"

    //用户银行卡列表
    private const val USER_BANK_LIST = "/index/user-card-list/"
    //银行卡列表
    private const val BANK_LIST = "/index/bank-list"
    //验证支付密码
    private const val VERIFY_PAY_PASS_WORD = "/index/verify-fund-password"
    //用户提现
    private const val USER_DEPOAIT = "/api/v1/withdraws/UserDeposit/"
    //绑定银行卡
    private const val USER_BIND_CARD = "/index/bind-card/"
    //上传头像
    const val USER_UPLOAD_AVATAR = "/index/upload-avatar"
    //是否有主播在直播
    private const val USER_IS_ANCHOR_LIVE = "/api/v1/user/is_anchor_live/"
    //修改密码
    private const val USER_MODIFY_PASSWORD = "/index/reset-password"

    /**
     * 获取用户信息
     */

    fun getUserInfo(function: ApiSubscriber<MineUserInfo>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineUserInfo>() {}
        subscriber.function()
//        getApiOther().get<MineUserInfo>("/userinfo/" + USER_INFO)
        getApiOther().get<MineUserInfo>(USER_INFO)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .subscribe(subscriber)
    }


    /**
     * 获取打赏记录
     */

    fun getRewardRecord(page: Int, limit: Int, function: ApiSubscriber<List<MineRewardRecordResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineRewardRecordResponse>>() {}
        subscriber.function()
        getApi().get<List<MineRewardRecordResponse>>(MINE_REWORD_RECORD)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", SpUtils.getInt(UserConstant.USER_ID))
                .params("page", page)
                .params("limit", limit)
                .subscribe(subscriber)
    }


    /**
     * 删除打赏记录
     */

    fun deleteRewardRecord(reward_id: Int, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().get<String>(MINE_REWORD_RECORD)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", SpUtils.getInt(UserConstant.USER_ID))
                .params("reward_id", reward_id)
                .subscribe(subscriber)
    }


    /**
     * 反馈意见
     */

    fun feedBack(text: String, phone: Long, qq: Int, email: String, function: ApiSubscriber<BaseApiBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        getApi().get<BaseApiBean>(MINE_FEED_BACK)
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
        getApi().get<List<MineAttentionResponse>>(ATTENTION)
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
//        getApiOther().post<String>("/userinfo/" + USER_INFO_EDIT)
        getApiOther().post<String>(USER_INFO_EDIT)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("nickname", nickname)
                .params("gender", gender)
                .params("profile", profile)
                .subscribe(subscriber)
    }

    /**
     * 兑换钻石
     */
    fun getUserChangeDiamond(diamond: String, password: String, function: ApiSubscriber<MineUserDiamond>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineUserDiamond>() {}
        subscriber.function()
        getApi().post<MineUserDiamond>(USER_CHANGE_DIAMOND)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", UserInfoSp.getUserId())
                .params("diamond", diamond)
                .params("password", password)
                .subscribe(subscriber)
    }

    /**
     * 获取钻石
     */
    fun getUserDiamond(function: ApiSubscriber<MineUserDiamond>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineUserDiamond>() {}
        subscriber.function()
        getApi().get<MineUserDiamond>(USER_DIAMOND)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", UserInfoSp.getUserId())
                .subscribe(subscriber)
    }


    /**
     * 获取余额
     */
    fun getUserBalance(function: ApiSubscriber<MineUserBalance>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineUserBalance>() {}
        subscriber.function()
//        getApiOther().get<MineUserBalance>("/userinfo/" + USER_BALANCE)
        getApiOther().get<MineUserBalance>(USER_BALANCE)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .subscribe(subscriber)
    }

    /**
     * 获取 支付通道列表
     */
    fun getPayTypeList(function: ApiSubscriber<List<MinePayTypeList>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MinePayTypeList>>() {}
        subscriber.function()
        getApi().post<List<MinePayTypeList>>(PAY_TYPE_LIST)
                .headers("token", UserInfoSp.getToken())
                .subscribe(subscriber)
    }


    /**
     * 获取 用户绑定的银行卡列表
     */
    fun getUserBankList(function: ApiSubscriber<List<MineUserBankList>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineUserBankList>>() {}
        subscriber.function()
//        getApiOther().get<List<MineUserBankList>>("/userinfo/" + USER_BANK_LIST)
        getApiOther().get<List<MineUserBankList>>(USER_BANK_LIST)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .subscribe(subscriber)
    }


    /**
     * 获取 银行卡列表
     */
    fun getBankList(function: ApiSubscriber<List<MineBankList>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineBankList>>() {}
        subscriber.function()
//        getApiOther().get<List<MineBankList>>("/userinfo/" + BANK_LIST)
        getApiOther().get<List<MineBankList>>(BANK_LIST)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .subscribe(subscriber)
    }


    /**
     * 验证支付密码
     */
    fun verifyPayPassWord(password: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
//        getApiOther().post<String>("/userinfo/" + VERIFY_PAY_PASS_WORD)
        getApiOther().post<String>(VERIFY_PAY_PASS_WORD)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("password", password)
                .subscribe(subscriber)
    }

    /**
     * 用户 提现
     */
    fun userGetCashOut(amount: Double, card_name: String, putforwoad_card: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().post<String>(USER_DEPOAIT)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", UserInfoSp.getUserId())
                .params("user_putforwoad", amount)
                .params("card_name", card_name)
                .params("putforwoad_card", putforwoad_card)
                .subscribe(subscriber)
    }

    /**
     *绑定银行卡
     */

    fun bingBankCard(bank_code: String, province: String, city: String, branch: String, realname: String, card_num: String, fund_password: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
//        getApiOther().post<String>("/userinfo" + USER_BIND_CARD)
        getApiOther().post<String>(USER_BIND_CARD)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("bank_code", bank_code)
                .params("province", province)
                .params("city", city)
                .params("branch", branch)
                .params("realname", realname)
                .params("card_num", card_num)
                .params("fund_password", fund_password)
                .subscribe(subscriber)
    }

    /**
     * 是否有主播在直播
     *
     */
    fun isAnchorLive(function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().get<String>(USER_IS_ANCHOR_LIVE)
//        getApiOther().post<String>(USER_UPLOAD_AVATAR)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", UserInfoSp.getUserId())
                .subscribe(subscriber)
    }

    /**
     * 修改密码
     */

    fun modifyPassWord(old_password: String, new_password: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
//        getApiOther().post<String>("/userinfo" + USER_MODIFY_PASSWORD)
        getApiOther().post<String>(USER_MODIFY_PASSWORD)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("old_password", old_password)
                .params("new_password", new_password)

                .subscribe(subscriber)
    }

    /**
     * 查询vip
     */
    fun getUserVip(function: ApiSubscriber<MineGetVip>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineGetVip>() {}
        subscriber.function()
        getApi().get<MineGetVip>(USER_LEVEL)
//        getApiOther().get<List<MineBankList>>(BANK_LIST)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", UserInfoSp.getUserId())
                .subscribe(subscriber)
    }

}