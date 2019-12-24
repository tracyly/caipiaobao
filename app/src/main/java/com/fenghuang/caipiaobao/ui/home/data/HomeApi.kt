package com.fenghuang.caipiaobao.ui.home.data

import com.fenghuang.caipiaobao.data.api.ApiConvert
import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.api.EmptySubscriber
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.google.gson.reflect.TypeToken
import com.pingerx.rxnetgo.rxcache.CacheMode
import io.reactivex.Flowable

/**
 *  author : Peter
 *  date   : 2019/9/5 13:02
 *  desc   : 首页模块的所有API接口
 */
object HomeApi : BaseApi {


    private const val HOME_BANNER_LIST = "api/v1/user/get_banner"
    private const val HONE_NOTICE = "api/v1/user/system_notice"
    private const val HOME_GAME_LIST = "api/v1/live/get_game_list"
    private const val HOME_HOT_LIVE_LIST = "api/v1/live/get_hot_list"
    private const val HOME_ALL_LIVE_LIST = "api/v1/live/get_all_list"
    private const val HOME_LIVE_POP = "api/v1/user/anchor_pop"
    private const val HOME_EXPERT_LIST = "api/v1/live/get_expert_list"
    private const val HOME_EXPERT_RECOMMEND = "api/v1/live/pro_red"
    private const val HOME_LIVE_CHAT_REWARD_LIST = "api/v1/live/get_reward_list"
    private const val HOME_LIVE_ROOM = "api/v1/live/get_live_room"
    private const val HOME_LIVE_SEND_RED_ENVELOPE = "api/v1/user/send_red/"
    private const val HOME_LIVE_PAY_PASSWORD = "api/v1/user/ver_pay_pass"
    private const val HOME_LIVE_RED_RECEIVE = "api/v1/user/receive_red"
    private const val HOME_LIVE_RED_RECEIVE_ROOM = "api/v1/live/get_room_red"
    private const val HOME_LIVE_ANCHOR_INFO = "api/v1/live/get_anchor_info"
    private const val HOME_LIVE_ANCHOR_ANCHOR_DYNAMIC = "api/v1/live/get_anchor_dynamic"
    private const val HOME_LIVE_ANCHOR_ANCHOR_DYNAMIC_LIKE = "api/v1/live/dynamic_like"
    private const val HOME_LIVE_ANCHOR_ANCHOR_20_NEWS = "api/v1/live/initChat/"
    private const val HOME_LIVE_RED_SET_PASS = "index/set-fund-password"
    private const val HOME_LIVE_GET_GIFT_LIST = "api/v1/live/get_gift_list/"
    private const val HOME_LIVE_SEND_GIFT = "api/v1/live/send_gift"
    private const val HOME_LIVE_IS_SET_PASS_WORD = "index/check-fund-password"
    private const val HOME_LIVE_ATTENTION = "/api/v1/live/follow/"
    private const val HOME_LIVE_AD_IMG = "/api/v1/user/get_ad_banner/"
    private const val LOTTERY_URL = "/api/v1/user/jump_to/"
    private const val ANCHOR_PUSH = "/api/v1/user/push_anchor/"

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
    fun getHomeHotLiveListResult(limit: Int, page: Int, cacheMode: CacheMode, function: ApiSubscriber<List<HomeLiveListResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeLiveListResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeLiveListResponse>>(HOME_HOT_LIVE_LIST)
                .cacheMode(cacheMode)
                .params("limit", limit)
                .params("page", page)
                .subscribe(subscriber)
    }

    fun getHomeHotLiveListResult(limit: Int, page: Int, cacheMode: CacheMode): Flowable<List<HomeLiveListResponse>> {
        return getApi()
                .get<List<HomeLiveListResponse>>(HOME_HOT_LIVE_LIST)
                .cacheMode(cacheMode)
                .params("limit", limit)
                .params("page", page)
                .converter(ApiConvert(type = object : TypeToken<List<HomeLiveListResponse>>() {}.type))
                .flowable()
    }


    /**
     * 获取所有直播
     */
    fun getHomeAllLiveListResult(limit: Int, page: Int, cacheMode: CacheMode, function: ApiSubscriber<List<HomeLiveListResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeLiveListResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeLiveListResponse>>(HOME_ALL_LIVE_LIST)
                .cacheMode(cacheMode)
                .params("limit", limit)
                .params("page", page)
                .subscribe(subscriber)
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
    fun getHomeLivePopResult(cacheMode: CacheMode, function: ApiSubscriber<List<HomeLivePopResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeLivePopResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeLivePopResponse>>(HOME_LIVE_POP)
                .cacheMode(cacheMode)
                .params("user_id", UserInfoSp.getUserId())
                .subscribe(subscriber)
    }

    /**
     * 获取专家直播列表
     */
    fun getHomeExpertListResult(limit: Int, page: Int, cacheMode: CacheMode, function: ApiSubscriber<List<HomeLiveListResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeLiveListResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeLiveListResponse>>(HOME_EXPERT_LIST)
                .cacheMode(cacheMode)
                .params("limit", limit)
                .params("page", page)
                .subscribe(subscriber)
    }

    fun getHomeExpertListResult(limit: Int, page: Int, cacheMode: CacheMode): Flowable<List<HomeLiveListResponse>> {
        return getApi()
                .get<List<HomeLiveListResponse>>(HOME_EXPERT_LIST)
                .cacheMode(cacheMode)
                .params("limit", limit)
                .params("page", page)
                .converter(ApiConvert(type = object : TypeToken<List<HomeLiveListResponse>>() {}.type))
                .flowable()
    }
    /**
     * 获取专家推荐列表
     */
    fun getHomeExpertRecommendResult(cacheMode: CacheMode, function: ApiSubscriber<List<HomeExpertRecommendResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeExpertRecommendResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeExpertRecommendResponse>>(HOME_EXPERT_RECOMMEND)
                .cacheMode(cacheMode)
                .cacheMode(cacheMode)
                .subscribe(subscriber)
    }

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
                .headers("token", UserInfoSp.getToken())
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
                .headers("token", UserInfoSp.getToken())
                .params("user_id", userId)
                .subscribe(subscriber)
    }

    /**
     * 设置支付密码
     * oldPassword= 老密码（选填） newPassword=新密码  newPasswordRepeat=确认新密码
     */
    fun getSettingPayPassword(oldPassword: String, newPassword: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>("/userinfo/" + HOME_LIVE_RED_SET_PASS)
//        getApiOther().post<String>(HOME_LIVE_RED_SET_PASS)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
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
                .headers("token", UserInfoSp.getToken())
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
        val request = getApi().get<List<HomeLiveRedRoom>>(HOME_LIVE_RED_RECEIVE_ROOM)
        if (userId != 0) {
            request.params("user_id", userId)
                    .params("anchor_id", anchorId)
        } else request.params("anchor_id", anchorId)
        request.subscribe(subscriber)
    }

    /**
     * 获取主播信息资料
     */
    fun getHomeLiveAnchorInfo(userId: Int, anchorId: Int, function: ApiSubscriber<HomeLiveAnchorInfoBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<HomeLiveAnchorInfoBean>() {}
        subscriber.function()
        getApi().get<HomeLiveAnchorInfoBean>(HOME_LIVE_ANCHOR_INFO)
                .params("user_id", userId)
                .params("anchor_id", anchorId)
                .subscribe(subscriber)
    }

    /**
     * 获取主播信息动态列表
     */
    fun getHomeLiveAnchorDynamicInfo(userId: Int, anchorId: Int, page: Int, function: ApiSubscriber<List<HomeLiveAnchorDynamicBean>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeLiveAnchorDynamicBean>>() {}
        subscriber.function()
        getApi().get<List<HomeLiveAnchorDynamicBean>>(HOME_LIVE_ANCHOR_ANCHOR_DYNAMIC)
                .params("user_id", userId)
                .params("anchor_id", anchorId)
                .params("page", page)
                .subscribe(subscriber)
    }

    fun getAnchorDynamicLike(userId: Int, dynamicId: Int, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().post<String>(HOME_LIVE_ANCHOR_ANCHOR_DYNAMIC_LIKE)
                .headers("token", UserInfoSp.getToken())
                .params("user_id", userId)
                .params("dynamic_id", dynamicId)
                .subscribe(subscriber)
    }

    /**
     * 获取最近20条消息
     */
    fun getRecentlyNews(userId: Int, anchorId: Int, function: ApiSubscriber<ArrayList<HomeLiveChatBean>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<ArrayList<HomeLiveChatBean>>() {}
        subscriber.function()
        val request = getApi().get<ArrayList<HomeLiveChatBean>>(HOME_LIVE_ANCHOR_ANCHOR_20_NEWS)
        if (userId != 0) {
            request.params("user_id", userId)
                    .params("anchor_id", anchorId)
        } else request.params("anchor_id", anchorId)
        request.subscribe(subscriber)
    }

    /**
     * 获取礼物列表
     */
    fun getGiftList(function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().get<String>(HOME_LIVE_GET_GIFT_LIST)
                .subscribe(subscriber)
    }

    /**
     * 送礼物
     */
    fun setGift(userId: Int, anchorId: Int, gift_id: Int, gift_num: Int, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().post<String>(HOME_LIVE_SEND_GIFT)
                .headers("token", UserInfoSp.getToken())
                .params("anchor_id", anchorId)
                .params("user_id", userId)
                .params("gift_id", gift_id)
                .params("gift_num", gift_num)
                .subscribe(subscriber)
    }

    /**
     * 验证是否设置支付密码
     */

    fun isSetPassWord(function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().get<String>("/userinfo/" + HOME_LIVE_IS_SET_PASS_WORD)
//        getApiOther().get<String>(HOME_LIVE_IS_SET_PASS_WORD)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .subscribe(subscriber)
    }

    /**
     * 关注Or 取关
     */
    fun setAttention(userId: Int, anchorId: Int, function: ApiSubscriber<HomeAttention>.() -> Unit) {
        val subscriber = object : ApiSubscriber<HomeAttention>() {}
        subscriber.function()
        getApi().post<HomeAttention>(HOME_LIVE_ATTENTION)
                .headers("token", UserInfoSp.getToken())
                .params("anchor_id", anchorId)
                .params("user_id", userId)
                .subscribe(subscriber)
    }


    /**
     * 广告图
     */

    fun getAdUrl(function: ApiSubscriber<List<HomeLiveAdImg>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeLiveAdImg>>() {}
        subscriber.function()
        getApi().get<List<HomeLiveAdImg>>(HOME_LIVE_AD_IMG)
                .subscribe(subscriber)
    }

    /**
     * 购彩网址
     */
    fun getLotteryUrl(function: ApiSubscriber<BetLotteryBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<BetLotteryBean>() {}
        subscriber.function()
        getApi().get<BetLotteryBean>(LOTTERY_URL)
                .subscribe(subscriber)

    }

    /**
     * 主播开播推送
     */
    fun anchorPush(function: ApiSubscriber<AnchorPush>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AnchorPush>() {}
        subscriber.function()
        getApi().get<AnchorPush>(ANCHOR_PUSH)
                .params("user_id", UserInfoSp.getUserId())
                .subscribe(subscriber)
    }
}