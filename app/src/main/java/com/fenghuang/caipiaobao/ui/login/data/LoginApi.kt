package com.fenghuang.caipiaobao.ui.login.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.api.EmptySubscriber
import com.fenghuang.caipiaobao.utils.UserInfoSp

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

    private const val ISFIRST = "/api/v1/Recharge/IsFirst"

    private const val SETTING_PASSWORD = "/index/sms-reset-pwd"

    private const val VERIFY_PASSWORD = "/index/verify-fund-password"

    //找回密码
    private const val GET_PASSWORD = "/home/retrieve-password"

    private const val JUMP = "/api/v1_1/user/jump_to/"


    /**
     * 获取验证码
     */

    fun userGetCode(phone: String, type: String, function: ApiSubscriber<RegisterCode>.() -> Unit) {
        val subscriber = object : ApiSubscriber<RegisterCode>() {}
        subscriber.function()
        getApiOther().post<RegisterCode>(GET_CODE)
//        getApiOther().post<RegisterCode>("/userinfo/" + GET_CODE)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("user_id", UserInfoSp.getUserId())
                .params("phone", phone)
                .params("type", type) //reg默认  login注册验证码
                .subscribe(subscriber)
    }

    /**
     * 修改登录密码
     */

    fun userGetCodeNoType(phone: String, captcha: String, new_pwd: String, type: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
//        getApiOther().post<String>("/userinfo/" +GET_PASSWORD)
        getApiOther().post<String>(GET_PASSWORD)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("user_id", UserInfoSp.getUserId())
                .params("phone", phone)
                .params("captcha", captcha)
                .params("new_pwd", new_pwd)
                .params("type", type)
                .subscribe(subscriber)
    }

    /**
     * 注册
     */

    fun userRegister(phone: String, code: String, password: String, is_auto_login: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
//        getApiOther().post<String>("/userinfo/" + REGISTER)
        getApiOther().post<String>(REGISTER)
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
//        getApiOther().post<LoginResponse>("/userinfo/" + LOGIN)
        getApiOther().post<LoginResponse>(LOGIN)
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
//        getApiOther().post<LoginResponse>("/userinfo/" + LOGIN)
        getApiOther().post<LoginResponse>(LOGIN)
                .params("phone", phoneNum)
                .params("captcha", captcha)
                .params("mode", 3)
                .params("is_auto_login", isAutoLogin)
                .subscribe(subscriber)
    }

    /**
     * 是否首冲
     */
    fun isFirstRecharge(userID: Int, token: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().post<String>(ISFIRST)
                .headers("token", token)
                .params("user_id", userID)
                .subscribe(subscriber)
    }

    /**
     * 验证码修改  登录/支付密码接口
     */
    fun modifyPassWord(phone: String, captcha: String, new_pwd: String, type: Int, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(SETTING_PASSWORD)
//        getApiOther().post<String>("/userinfo/" + SETTING_PASSWORD)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("phone", phone)
                .params("captcha", captcha)
                .params("new_pwd", new_pwd)
                .params("type", type)
                .subscribe(subscriber)
    }

    /**
     * 验证支付密码
     */

    fun modifyPassWord(password: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(VERIFY_PASSWORD)
//        getApiOther().post<String>("/userinfo/" + VERIFY_PASSWORD)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("password", password)
                .subscribe(subscriber)
    }
}