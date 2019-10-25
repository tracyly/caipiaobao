package com.fenghuang.caipiaobao.ui.lottery.data

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/21- 19:23
 * @ Describe
 *
 */

data class LotteryTypeResponse(var lottery_id: Int, var cname: String, var logo_url: String)

data class LotteryCodeNewResponse(var lottery_id: Int, var issue: String, var code: String, var next_lottery_time: String, var input_time: String)

data class LotteryCodeHistoryResponse(var issue: String, var code: String, var input_time: String)

data class LotteryExpertPlanResponse(var id: Int, var lottery_id: Int, var issue: String, var code: String, var hit_rate: String, var created: String, var avatar: String, var nickname: String)

data class LotteryGetExpert(var lottery_id: Int, var issue: String)