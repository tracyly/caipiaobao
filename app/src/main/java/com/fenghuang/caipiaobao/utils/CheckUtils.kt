package com.fenghuang.caipiaobao.utils

import android.text.TextUtils

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/19- 13:50
 * @ Describe 正则类
 *
 */
object CheckUtils {
    /**
     * 验证手机号码是否合法
     * 176, 177, 178;
     * 180, 181, 182, 183, 184, 185, 186, 187, 188, 189;
     * 145, 147;
     * 130, 131, 132, 133, 134, 135, 136, 137, 138, 139;
     * 150, 151, 152, 153, 155, 156, 157, 158, 159;
     *
     * "13"代表前两位为数字13,
     * "[0-9]"代表第二位可以为0-9中的一个,
     * "[^4]" 代表除了4
     * "\\d{8}"代表后面是可以是0～9的数字, 有8位。
     */
    fun isMobileNumber(mobiles: String): Boolean {
        val telRegex = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$"
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex.toRegex())
    }
}

