package com.fenghuang.caipiaobao.ui.home.data

import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.data.api.ApiConvert
import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.api.EmptySubscriber
import com.google.gson.reflect.TypeToken
import com.pingerx.rxnetgo.rxcache.CacheMode
import io.reactivex.Flowable

/**
 *  author : Peter
 *  date   : 2019/9/5 13:02
 *  desc   : 首页模块的所有API接口
 */
object HomeApi : BaseApi {



    private const val HOME_BANNER_LIST = "/index.php/api/v1/user/get_banner"
    private const val HONE_NOTICE = "/index.php/api/v1/user/system_notice"
    private const val HOME_GAME_LIST = "/index.php/api/v1/live/get_game_list"
    private const val HOME_HOT_LIVE_LIST = "/index.php/api/v1/live/get_hot_list"
    private const val HOME_LIVE_POP = "/index.php/api/v1/user/anchor_pop"
    private const val HOME_EXPERT_LIST = "/index.php/api/v1/live/get_expert_list"
    private const val HOME_EXPERT_RECOMMEND = "/index.php/api/v1/live/pro_red"
    private const val HOME_LIVE_CHAT_REWARD_LIST = "/index.php/api/v1/live/get_reward_list"
    private const val HOME_LIVE_ROOM = "index.php/api/v1/live/get_live_room"
    private const val HOME_LIVE_SEND_RED_ENVELOPE = "index.php/api/v1/user/send_red"
    private const val HOME_LIVE_PAY_PASSWORD = "index.php/api/v1/user/ver_pay_pass"
    private const val HOME_LIVE_RED_RECEIVE = "api/v1/user/receive_red"
    private const val HOME_LIVE_RED_RECEIVE_ROOM = "api/v1/live/get_room_red"

    private const val HOME_LIVE_RED_SET_PASS = "index/set-fund-password"

    /**
     * 获取首页轮播图列表
     */
    fun getHomeBannerResult(cacheMode: CacheMode, function: ApiSubscriber<List<HomeBannerResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeBannerResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeBannerResponse>>(HOME_BANNER_LIST)
                .cacheMode(cacheMode)
                .subscribe(subscriber)
    }

    /**
     * 获取首页公告信息
     */
    fun getHomeNoticeResult(cacheMode: CacheMode, function: ApiSubscriber<List<HomeNoticeResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeNoticeResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeNoticeResponse>>(HONE_NOTICE)
                .cacheMode(cacheMode)
                .subscribe(subscriber)
    }

    /**
     * 获取首页游戏榜
     */
    fun getHomeGameListResult(cacheMode: CacheMode, function: ApiSubscriber<List<HomeGameListResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeGameListResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeGameListResponse>>(HOME_GAME_LIST)
                .cacheMode(cacheMode)
                .subscribe(subscriber)
    }

    /**
     * 获取热门直播
     */
//    fun getHomeHotLiveListResult(function: ApiSubscriber<List<HomeLiveListResponse>>.() -> Unit) {
//        val subscriber = object : ApiSubscriber<List<HomeLiveListResponse>>() {}
//        subscriber.function()
//        getApi().get<List<HomeLiveListResponse>>(HOME_HOT_LIVE_LIST)
//                .subscribe(subscriber)
//    }
    fun getHomeHotLiveListResult(cacheMode: CacheMode): Flowable<List<HomeLiveListResponse>> {
        return getApi()
                .get<List<HomeLiveListResponse>>(HOME_HOT_LIVE_LIST)
                .cacheMode(cacheMode)
                .converter(ApiConvert(type = object : TypeToken<List<HomeLiveListResponse>>() {}.type))
                .flowable()
    }

    /**
     * 获取直播预告
     */
    fun getHomeLivePopResult(cacheMode: CacheMode): Flowable<List<HomeLivePopResponse>> {
        return getApi()
                .get<List<HomeLivePopResponse>>(HOME_LIVE_POP)
                .cacheMode(cacheMode)
                .converter(ApiConvert(type = object : TypeToken<List<HomeLivePopResponse>>() {}.type))
                .flowable()
    }
//    fun getHomeLivePopResult(function: ApiSubscriber<List<HomeLivePopResponse>>.() -> Unit) {
//        val subscriber = object : ApiSubscriber<List<HomeLivePopResponse>>() {}
//        subscriber.function()
//        getApi().get<List<HomeLivePopResponse>>(HOME_LIVE_POP)
//                .subscribe(subscriber)
//    }

    /**
     * 获取专家直播列表
     */
    fun getHomeExpertListResult(cacheMode: CacheMode): Flowable<List<HomeLiveListResponse>> {
        return getApi()
                .get<List<HomeLiveListResponse>>(HOME_EXPERT_LIST)
                .cacheMode(cacheMode)
                .converter(ApiConvert(type = object : TypeToken<List<HomeLiveListResponse>>() {}.type))
                .flowable()
    }

    /**
     * 获取专家推荐列表
     */
    fun getHomeExpertRecommendResult(cacheMode: CacheMode): Flowable<List<HomeExpertRecommendResponse>> {
        return getApi()
                .get<List<HomeExpertRecommendResponse>>(HOME_EXPERT_RECOMMEND)
                .cacheMode(cacheMode)
                .converter(ApiConvert(type = object : TypeToken<List<HomeExpertRecommendResponse>>() {}.type))
                .flowable()
    }

    /**
     * 获取直播间礼物榜单
     */
    fun getHomeLiveChatRewardResult(anchorId: Int, function: ApiSubscriber<List<HomeLiveRoomRewardBean>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeLiveRoomRewardBean>>() {}
        subscriber.function()
        getApi().get<List<HomeLiveRoomRewardBean>>(HOME_LIVE_CHAT_REWARD_LIST)
                .params("anchor_id", anchorId)
                .subscribe(subscriber)
    }

    /**
     * 获取直播间播放资源
     */
    fun getHomeLiveRoomResult(anchorId: Int, userId: Int, function: ApiSubscriber<HomeLiveRoomBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<HomeLiveRoomBean>() {}
        subscriber.function()
        getApi().get<HomeLiveRoomBean>(HOME_LIVE_ROOM)
                .params("anchor_id", anchorId)
                .params("user_id", userId)
                .subscribe(subscriber)
    }

    /**
     * 直播间发送红包
     */
    fun getHomeLiveSendRedEnvelope(anchorId: Int, userId: Int, amount: Int, num: Int, text: String, password: String, function: ApiSubscriber<HomeLiveRedEnvelopeBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<HomeLiveRedEnvelopeBean>() {}
        subscriber.function()
        getApi().post<HomeLiveRedEnvelopeBean>(HOME_LIVE_SEND_RED_ENVELOPE)
                .headers("token", SpUtils.getString(UserConstant.TOKEN))
                .params("anchor_id", anchorId)
                .params("user_id", userId)
                .params("amount", amount)
                .params("num", num)
                .params("text", text)
                .params("password", password)
                .subscribe(subscriber)
    }

    /**
     * 验证是否设置支付密码
     */
    fun getIsPayPassword(userId: Int, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().post<String>(HOME_LIVE_PAY_PASSWORD)
                .headers("token", SpUtils.getString(UserConstant.TOKEN))
                .params("user_id", userId)
                .subscribe(subscriber)
    }

    /**
     * 设置支付密码
     * oldPassword= 老密码（选填） newPassword=新密码  newPasswordRepeat=确认新密码
     */
    fun getSettingPayPassword(oldPassword: String, newPassword: String, newPasswordRepeat: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(HOME_LIVE_RED_SET_PASS)
//                .headers("token", TOKEN)
                .headers("Authorization", "Bearer " + SpUtils.getString(UserConstant.TOKEN))
                .params("old_password", oldPassword)
                .params("new_password", newPassword)
//                .params("new_password_repeat", newPasswordRepeat)
                .subscribe(subscriber)
    }

    /**
     * 抢红包
     */
    fun getRedReceive(userId: Int, rid: Int, function: ApiSubscriber<HomeLiveRedReceiveBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<HomeLiveRedReceiveBean>() {}
        subscriber.function()
        getApi().post<HomeLiveRedReceiveBean>(HOME_LIVE_RED_RECEIVE)
                .headers("token", SpUtils.getString(UserConstant.TOKEN))
                .params("user_id", userId)
                .params("rid", rid)
                .subscribe(subscriber)
    }

    /**
     * 获取直播间红包队列
     */
    fun getRoomRed(userId: Int, anchorId: Int, function: ApiSubscriber<List<HomeLiveRedRoom>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeLiveRedRoom>>() {}
        subscriber.function()
        getApi().get<List<HomeLiveRedRoom>>(HOME_LIVE_RED_RECEIVE_ROOM)
                .headers("token", SpUtils.getString(UserConstant.TOKEN))
                .params("user_id", userId)
                .params("anchor_id", anchorId)
                .subscribe(subscriber)
    }


}