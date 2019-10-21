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
                            var userName: String,
                            var gift_id: Int,
                            var gift_type: Int,
                            var gift_name: String,
                            var gift_price: Float,
                            var gift_num: Int,
                            var vip: Int,
                            var r_id: Int) : Parcelable

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

// 发送红包
data class HomeLiveRedEnvelopeBean(var rid: Int)

// 接收红包消息通知
data class HomeLiveRedMessageBean(var gift_type: Int,
                                  var rid: Int)

// 抢红包
data class HomeLiveRedReceiveBean(var amount: String,
                                  var send_text: String,
                                  var send_user_name: String,
                                  var count: Int,
                                  var send_user_avatar: String)

// 红包队列
data class HomeLiveRedRoom(var id: Int,
                           var text: String)

// 主播信息动态数据
data class HomeLiveAnchorDynamicBean(var created: String,
                                     var id: Int,
                                     var images: List<String>,
                                     var like: Int,
                                     var nickname: String,
                                     var title: String,
                                     var is_like: Int)