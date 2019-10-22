package com.fenghuang.caipiaobao.ui.lottery.data

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 17:40
 * @ Describe
 *
 */

data class LotteryDataBean(var title: String, var image: Int)

data class LotteryOpenCodeDataBean(var code: String)

data class LotteryHistoryOpenCodeBean(var code: String, var issue: String, var input_time: String)
