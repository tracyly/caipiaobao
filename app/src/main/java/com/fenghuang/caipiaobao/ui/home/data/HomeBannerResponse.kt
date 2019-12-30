package com.fenghuang.caipiaobao.ui.home.data

import com.google.gson.annotations.SerializedName

/**
 *  author : Peter
 *  date   : 2019/9/5 13:10
 */

// 轮播图
data class HomeBannerResponse(var content: String,
                              var image_url: String,
                              var name: String,
                              var title: String,
                              var type: Int,
                              var url: String)

// 公告信息
data class HomeNoticeResponse(var action: String,
                              var content: String,
                              var gnrtime: String,
                              var id: Int,
                              var msgtype: Int,
                              var title: String,
                              var trgttype: Int)

// 游戏榜
data class HomeGameListResponse(var anchor_id: Int,
                                var game_id: Int,
                                var image: String,
                                var live_status: Int,
                                var live_status_txt: String,
                                var name: String)

// 热门直播 & 专家直播
data class HomeLiveListResponse(var anchor_id: Int,
                                var avatar: String,
                                var live_intro: String,
                                var live_status: Int,
                                var live_status_txt: String,
                                var name: String,
                                var nickname: String,
                                var online: Int,
                                var tags: List<HomeExpertTags>)

data class HomeExpertTags(var icon: String,
                          var title: String)

// 直播预告
data class HomeLivePopResponse(var aid: Int,
                               var avatar: String,
                               var isFollow: Boolean,
                               var name: String,
                               var nickname: String,
                               var starttime: Long,
                               var endtime: Long,
                               var livestatus: Int)

data class HomeExpertRecommendResponse(var id: Int,
                                       var pro_avatar: String,
                                       var pro_name: String,
                                       var pro_type: String,
                                       var profit: String)

// 点击头像跳转标记消息体
data class HomeClickMine(var isClick: Boolean)

//小视屏点击跳转
data class HomeClickVideo(var list: List<DataBean.HomeGameListResponse>)

//关注取关
data class HomeAttention(var isFollow: Boolean)


class DataBean {
    @SerializedName("1")
    var `_$1`: List<HomeGameListResponse>? = null
    @SerializedName("2")
    var `_$2`: List<HomeGameListResponse>? = null
    @SerializedName("3")
    var `_$3`: List<HomeGameListResponse>? = null
    @SerializedName("4")
    var `_$4`: List<HomeGameListResponse>? = null

    class HomeGameListResponse {
        var type: Int = 0
        var anchor_id: Int = 0
        var game_id: Int = 0
        var name: String? = null
        var image: String? = null
        var image_pc: String? = null
        var live_status: Int = 0
        var live_intro: String? = null
        var online: Int = 0
        var lottery_id: Int = 0
        var live_status_txt: String? = null
    }

}


