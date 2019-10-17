package com.fenghuang.caipiaobao.ui.login.data

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 15:36
 * @ Describe
 *
 */

data class LoginResponse(var token: String,
                         var user_id: Int,
                         var expire: Long,
                         var avatar: String,
                         var password_not_set: Int
)

data class RegisterCode(var code: String)
