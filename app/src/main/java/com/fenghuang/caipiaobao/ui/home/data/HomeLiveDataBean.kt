package com.fenghuang.caipiaobao.ui.home.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 *  author : Peter
 *  date   : 2019/8/10 18:28
 *  desc   : 直播数据封装的bean
 */
// 聊天室
@Parcelize
data class HomeLiveChatBean(var position: String,
                            var room_id: String,
                            var sendTime: Long,
                            var sendTimeTxt: String,
                            var gift_text: String,
                            var size: String,
                            var type: String,
                            var text: String,
                            var color: String,
                            var isMe: Boolean,
                            var user_id: Int?,
                            var userType: String,
                            var userName: String,
                            var gift_id: Int?,
                            var gift_type: Int?,
                            var gift_name: String,
                            var gift_price: Float?,
                            var gift_num: Int?,
                            var vip: Int?,
                            var icon: String,
                            var r_id: Int?, var avatar: String?, var code: Int?) : Parcelable

@Parcelize
data class HomeLiveChatBeanNew(var position: String,
                               var room_id: String,
                               var sendTime: Long,
                               var sendTimeTxt: String,
                               var gift_text: String,
                               var size: String,
                               var type: String,
                               var text: String,
                               var color: String,
                               var isMe: Boolean,
                               var user_id: String,
                               var userType: String,
                               var userName: String,
                               var gift_id: String,
                               var gift_type: String,
                               var gift_name: String,
                               var gift_price: String,
                               var gift_num: String,
                               var vip: String,
                               var icon: String,
                               var r_id: String, var avatar: String?, var code: String, var msg: String) : Parcelable


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
                            var isFollow: Boolean,
                            var liveInfo: List<HomeLiveRoomListBean>)

data class HomeLiveRoomListBean(var liveUrl: HomeLiveRoomListLiveUrlBean,
                                var type: String)

data class HomeLiveRoomListLiveUrlBean(var fluentPullUrl: String,
                                       var hdPullUrl: String,
                                       var highPullUrl: String,

                                       var originPullUrl: String)

// gif礼物-------------------


data class HomeLiveChatGifBean(var id: Int, var type: Int,
                               var name: String,
                               var amount: String,
                               var icon: String,
                               var isSelect: Boolean
)
data class HomeLiveChatGifTitleBean(@SerializedName("1") var `_$1`: List<HomeLiveChatGifBean>? = null, @SerializedName("2") var `_$2`: List<HomeLiveChatGifBean>? = null, @SerializedName("3") var `_$3`: List<HomeLiveChatGifBean>? = null, @SerializedName("4") var `_$4`: List<HomeLiveChatGifBean>? = null, @SerializedName("5") var `_$5`: List<HomeLiveChatGifBean>? = null)

// gif礼物------------------- end
// 发送红包
data class HomeLiveRedEnvelopeBean(var rid: Int)

// 接收红包消息通知
data class HomeLiveRedMessageBean(var gift_type: Int,
                                  var rid: Int,
                                  var gift_text: String,
                                  var userName: String
)

// 抢红包
data class HomeLiveRedReceiveBean(var amount: String,
                                  var send_text: String,
                                  var send_user_name: String,
                                  var count: Int,
                                  var send_user_avatar: String)

// 红包队列
data class HomeLiveRedRoom(var id: Int,
                           var text: String)


data class HomeLiveAnchorInfoBean(var anchor_id: Int,
                                  var avatar: String,
                                  var zan: String,
                                  var duration: Int,
                                  var fans: Int,
                                  var follow_num: Int,
                                  var giftList: List<HomeLiveAnchorGiftListBean>,
                                  var giftNum: Int,
                                  var isFollow: Boolean,
                                  var level: Int,
                                  var liveStatus: Int,
                                  var live_record: List<HomeLiveAnchorLiveRecordBean>,
                                  var lottery: List<HomeLiveAnchorLotteryBean>,
                                  var nickname: String,
                                  var sex: Int,
                                  var age: Int,
                                  var liveStartTime: Long,
                                  var liveEndTime: Long,
                                  var sign: String,
                                  var tagList: List<HomeLiveAnchorTagListBean>) : Serializable

data class HomeLiveAnchorGiftListBean(var gift_id: Int,
                                      var icon: String) : Serializable

data class HomeLiveAnchorLiveRecordBean(var name: String,
                                        var startTime: Long,
                                        var startTimeTxt: String,
                                        var tip: String) : Serializable

data class HomeLiveAnchorLotteryBean(var name: String) : Serializable
data class HomeLiveAnchorTagListBean(var icon: String,
                                     var title: String) : Serializable

// 主播信息动态数据
data class HomeLiveAnchorDynamicBean(var anchor_id: Int,
                                     var avatar: String,
                                     var create_time: Long,
                                     var create_time_txt: String,
                                     var id: Int,
                                     var isZan: Boolean,
                                     var media: List<String>,
                                     var nickname: String,
                                     var pls: Int,
                                     var shares: Int,
                                     var text: String,
                                     var time_tip: String,
                                     var zans: Int)


//礼物小动画Rx
data class HomeLiveSmallAnimatorBean(var gift_id: Int, var git_name: String, var gift_icon: String, var user_id: Int, var user_icon: String, var user_name: String)

//關閉鍵盤
data class HomeLiveColseSoftKeyBord(var isClose: Boolean)


//广告图
data class HomeLiveAdImg(var image_url: String)

data class BetLotteryBean(var betting: String, var customer: String, var gameUrl: String, var protocol: String)

//开播推送
data class AnchorPush(var anchor_id: String, var anchor_avatar: String, var anchor_nickname: String)


//关闭更多直播
data class CloseMore(var isClose: Boolean)

/**
 * version_data : 版本信息
 * version_data.enforce : 是否强制更新 1是 0-否
 * version_data.version : 客户端版本号
 * version_data.newversion : 新版本号
 * version_data.downloadurl : 下载地址
 * version_data.packagesize : 包大小
 * version_data.upgradetext : 更新内容
 */

data class UpdateData(var version_data: Update? = null)

data class Update(var enforce: Int = 0,
                  var version: String? = null,
                  var newversion: String? = null,
                  var downloadurl: String? = null,
                  var packagesize: String? = null,
                  var upgradetext: String? = null)

