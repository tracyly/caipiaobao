package com.fenghuang.caipiaobao.ui.home.data

import com.fenghuang.caipiaobao.data.api.ApiConvert
import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
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
}