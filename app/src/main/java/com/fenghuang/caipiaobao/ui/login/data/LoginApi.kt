package com.fenghuang.caipiaobao.ui.login.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.api.EmptySubscriber

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/16- 16:31
 * @ Describe
 *
 */

object LoginApi : BaseApi {

    private const val GET_CODE = "/reg/send-sms"

    private const val LOGIN = "/login/index"

    private const val REGISTER = "/reg/index"

    /**
     * 获取验证码
     */

    fun userGetCode(phone: String, type: String, function: ApiSubscriber<RegisterCode>.() -> Unit) {
        val subscriber = object : ApiSubscriber<RegisterCode>() {}
        subscriber.function()
        LoginApi.getApiOther().post<RegisterCode>(GET_CODE)
                .params("phone", phone)
                .params("type", type) //reg默认  login注册验证码
                .subscribe(subscriber)
    }

    /**
     * 注册
     */

    fun userRegister(phone: String, code: String, password: String, is_auto_login: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        LoginApi.getApiOther().post<String>(REGISTER)
                .params("phone", phone)
                .params("password", password)
                .params("captcha", code)
                .params("mode", 3)
                .params("is_auto_login", is_auto_login)
                .subscribe(subscriber)
    }

    /**
     * 密码登录
     */

    fun userLoginWithPassWord(userName: String, passWord: String, function: ApiSubscriber<LoginResponse>.() -> Unit) {
        val subscriber = object : ApiSubscriber<LoginResponse>() {}
        subscriber.function()
        LoginApi.getApiOther().post<LoginResponse>(LOGIN)
                .params("username", userName)
                .params("password", passWord)
                .params("mode", 1)
                .subscribe(subscriber)
    }

    /**
     * 验证码登录
     */

    fun userLoginWithIdentify(phoneNum: String, captcha: String, isAutoLogin: Int, function: ApiSubscriber<LoginResponse>.() -> Unit) {
        val subscriber = object : ApiSubscriber<LoginResponse>() {}
        subscriber.function()
        LoginApi.getApiOther().post<LoginResponse>(LOGIN)
                .params("phone", phoneNum)
                .params("captcha", captcha)
                .params("mode", 3)
                .params("is_auto_login", isAutoLogin)
                .subscribe(subscriber)
    }
}