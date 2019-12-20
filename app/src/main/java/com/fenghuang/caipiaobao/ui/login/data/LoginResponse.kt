package com.fenghuang.caipiaobao.ui.login.data

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/14- 15:36
 * @ Describe
 *
 */

//登录成功后 expire 过期时间，时间戳
data class LoginSuccess(var loginOrExit: Boolean, var token: String, var userId: Int, var userPhone: String, var password_not_set: Int, var avatar: String)



data class LoginResponse(var token: String,
                         var user_id: Int,
                         var expire: Long,
                         var avatar: String,
                         var password_not_set: Int,
                         var user_type: String
)

data class RegisterCode(var code: String)


data class LoginExit(var exit: Boolean)

data class LoginExitStart(var exit: Boolean)