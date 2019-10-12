package com.fenghuang.caipiaobao.ui.home.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *  author : Peter
 *  date   : 2019/8/10 18:28
 *  desc   : 直播数据封装的bean
 */
// 聊天室
@Parcelize
data class HomeLiveChatBean(var position: String,
                            var room_id: String,
                            var sendTime: String,
                            var size: String,
                            var type: String,
                            var text: String,
                            var color: String,
                            var isMe: Boolean,
                            var user_id: String,
                            var userName: String) : Parcelable

// 横屏时发送的聊天弹幕消息
data class HomeLiveChatPostEvenBean(var content: String)

// 聊天室礼物榜单
data class HomeLiveRoomRewardBean(var avatar: String)

// 直播房间
data class HomeLiveRoomBean(var anchor_id: Int,
                            var nickname: String,
                            var avatar: String,
                            var isTop: Int,
                            var live_status: Int,
                            var fans: Int,
                            var cover: String,
                            var liveStartTime: Int,
                            var liveEndTime: Int,
                            var online: Int,
                            var is_top_txt: String,
                            var live_status_txt: String,
                            var liveInfo: List<HomeLiveRoomListBean>)

data class HomeLiveRoomListBean(var liveUrl: HomeLiveRoomListLiveUrlBean,
                                var type: String)

data class HomeLiveRoomListLiveUrlBean(var fluentPullUrl: String,
                                       var hdPullUrl: String,
                                       var highPullUrl: String,

                                       var originPullUrl: String)

// gif礼物
data class HomeLiveChatGifBean(var gifUrl: Int,
                               var title: String,
                               var gold: Int,
                               var isSelect: Boolean)