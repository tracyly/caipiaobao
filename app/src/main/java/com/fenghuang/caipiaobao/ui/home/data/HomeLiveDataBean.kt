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

data class HomeLiveRoomTitleBean(var imageLogo: String)