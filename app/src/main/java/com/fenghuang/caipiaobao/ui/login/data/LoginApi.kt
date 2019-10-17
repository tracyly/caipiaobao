package com.fenghuang.caipiaobao.ui.login.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.bean.BaseApiBean
import me.jessyan.autosize.utils.LogUtils

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

    fun userGetCode(phone: String, function: ApiSubscriber<RegisterCode>.() -> Unit) {
        val subscriber = object : ApiSubscriber<RegisterCode>() {}
        subscriber.function()
        LoginApi.getApiOther().post<RegisterCode>(GET_CODE)
                .params("phone", phone)
                .subscribe(subscriber)
    }

    fun userRegister(phone: String, code: String, function: ApiSubscriber<BaseApiBean>.() -> Unit) {
        LogUtils.e("Register-------->$code")
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        LoginApi.getApiOther().post<BaseApiBean>(REGISTER)
                .params("phone", phone)
                .params("captcha", code)
                .params("mode", 3)
                .subscribe(subscriber)
    }

    fun userLogin(username: String, password: String, function: ApiSubscriber<LoginResponse>.() -> Unit) {
        val subscriber = object : ApiSubscriber<LoginResponse>() {}
        subscriber.function()
        LoginApi.getApiOther().post<LoginResponse>(LOGIN)
                .params("username", username)
                .params("password", password)
                .params("mode", 1)
                .subscribe(subscriber)
    }
}