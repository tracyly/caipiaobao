package com.fenghuang.caipiaobao.ui.live.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *  author : Peter
 *  date   : 2019/8/10 18:28
 *  desc   : 直播数据封装的bean
 */

data class LiveDataBean(var url: String,
                        var title: String,
                        var name: String)

data class LiveTitleDataBean(var title: String,
                             var url: String,
                             var resultList: List<LiveDataBean>)

// 聊天室
@Parcelize
data class LiveChatBean(var position: String,
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
data class LiveChatPostEvenBean(var content: String)