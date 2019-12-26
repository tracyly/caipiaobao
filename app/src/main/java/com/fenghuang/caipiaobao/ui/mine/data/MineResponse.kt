package com.fenghuang.caipiaobao.ui.mine.data

import java.io.Serializable
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 15:36
 * @ Describe
 *
 */


// 用户信息
data class MineUserInfo(var username: String, var nickname: String, var profile: String, var avatar: String, var gender: Int, var phone: String)


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
                                 var anchor_id: Int,
                                 var live_status: Int,
                                 var avatar: String,
                                 var intro: String, var sign: String) : Serializable

//用户余额
data class MineUserBalance(var balance: BigDecimal)

//用户钻石
data class MineUserDiamond(var diamond: String)

//支付通道列表
data class MinePayTypeList(var id: Int, var channels_type: String, var low_money: String, var high_money: String, var icon: String, var apiroute: String)

//支付Url
data class MinePayUrl(var url: String)

//银行卡列表
data class MineBankList(var name: String, var img: String, var code: String)

//用户银行卡里列表
data class MineUserBankList(var id: Int, var bank_id: Int, var realname: String, var card_num: String, var province: String, var city: String, var bank_img: String, var bank_name: String)

//Rx存储用户选择的银行卡
data class MineSaveBank(var data: MineUserBankList)

//更新用户选择的银行卡
data class MineUpDateBank(var isUpdate: Boolean)

//更新余额
data class MineUpDateMoney(var money: String)
//Rx更新余额用户信息
data class MineUpDateUser(var upDateMoney: Boolean, var upDateAll: Boolean, var upDateDiamond: Boolean)

//密码输入错误此时
data class MinePassWordTime(var remain_times: Int)

//查询Vip
data class MineGetVip(var vip: Int)

//支付密码是否设置
data class MineIsSetPayPass(var isSet: Boolean)

