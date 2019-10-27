package com.fenghuang.caipiaobao.utils

import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.caipiaobao.constant.UserConstant

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/26- 15:12
 * @ Describe 缓存个人信息
 *
 */

object UserInfoSp {

    /**
     * 是否登录
     */
    fun putIsLogin(isLogin:Boolean){
        SpUtils.putBoolean(UserConstant.USER_LOGIN, isLogin)
    }

    fun getIsLogin(): Boolean {
        return SpUtils.getBoolean(UserConstant.USER_LOGIN)
    }

    /**
     * token
     */
    fun putToken(token: String) {
        SpUtils.putString(UserConstant.TOKEN, token)
        SpUtils.putString(UserConstant.TOKEN_WITH_BEARER, "Bearer $token")
    }

    fun getToken(): String? {
        return SpUtils.getString(UserConstant.TOKEN)
    }

    fun getTokenWithBearer(): String? {
        return SpUtils.getString(UserConstant.TOKEN_WITH_BEARER)
    }

    /**
     * 用户ID
     */

    fun putUserId(id: Int) {
        SpUtils.putInt(UserConstant.USER_ID, id)
    }

    fun getUserId(): Int {
        return SpUtils.getInt(UserConstant.USER_ID, 0)
    }

    /**
     * 用户名称 nickName
     */
    fun putUserNickName(nickName: String) {
        SpUtils.putString(UserConstant.USER_NICK_NAME, nickName)
    }

    fun getUserNickName(): String? {
        return SpUtils.getString(UserConstant.USER_NICK_NAME)
    }

    /**
     * 用户名 Name
     */
    fun putUserName(Name: String) {
        SpUtils.putString(UserConstant.USER_NAME, Name)
    }

    fun getUserName(): String? {
        return SpUtils.getString(UserConstant.USER_NAME)
    }

    /**
     * 用户名头像
     */
    fun putUserPhoto(url: String) {
        SpUtils.putString(UserConstant.USER_AVATAR, url)
    }

    fun getUserPhoto(): String? {
        return SpUtils.getString(UserConstant.USER_AVATAR)
    }

    /**
     * 银行卡
     */
    fun putUserBank(bankCard: String) {
        SpUtils.putString(UserConstant.USER_BANK, JsonUtils.toJson(bankCard))
    }

    fun getUserBank(): String? {
        return SpUtils.getString(UserConstant.USER_BANK)
    }

    /**
     * 手机号
     */
    fun putUserPhone(phone: String) {
        SpUtils.putString(UserConstant.USER_PHONE, phone)
    }

    fun getUserPhone(): String? {
        return SpUtils.getString(UserConstant.USER_PHONE,defValue = "0")
    }

    /**
     * 是否设置支付密码
     */
    fun putUserIsSetPassWord(password_not_set: Int) {
        SpUtils.putInt(UserConstant.USER_SET_PAY_PASSWORD, password_not_set)
    }

    fun getUserIsSetPassWord(): Int {
        return SpUtils.getInt(UserConstant.USER_SET_PAY_PASSWORD, 0)
    }

    /**
     * 性别
     */
    fun putUserSex(sex: Int) {
        SpUtils.putInt(UserConstant.USER_SEX, sex)
    }

    fun getUserSex(): Int {
        return SpUtils.getInt(UserConstant.USER_SEX, 1)
    }
    /**
     * 个性签名
     */
    fun putUserProfile(profile: String) {
        SpUtils.putString(UserConstant.USER_PROFILE, profile)
    }

    fun getUserProfile(): String? {
        return SpUtils.getString(UserConstant.USER_PROFILE)
    }
}