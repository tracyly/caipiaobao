package com.fenghuang.caipiaobao.ui.mine.data

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 15:36
 * @ Describe
 *
 */


// 用户信息
data class MineUserInfo(var username: String, var nickname: String, var profile: String, var avatar: String, var gender: Int)


// 打赏记录
data class MineRewardRecordResponse(var id: Int,
                                    var nickname: String,
                                    var create_time: Long,
                                    var giftname: String,
                                    var gift_num: Int,
                                    var avatar: String
)

// 关注列表
data class MineAttentionResponse(var type: String,
                                 var nickname: String,
                                 var anchor_id: String,
                                 var live_status: String,
                                 var avatar: String,
                                 var intro: String)

//修改用户信息
data class MineEditUserInfo(var username: String,var sex : Int ,var profile:String ,var avatar:String)