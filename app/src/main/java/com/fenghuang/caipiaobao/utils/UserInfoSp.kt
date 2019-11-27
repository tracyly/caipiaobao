package com.fenghuang.caipiaobao.utils

import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.mine.data.MineUserBankList

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
    fun putIsLogin(isLogin: Boolean) {
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
     * 手机号
     */
    fun putUserPhone(phone: String) {
        SpUtils.putString(UserConstant.USER_PHONE, phone)
    }

    fun getUserPhone(): String? {
        return SpUtils.getString(UserConstant.USER_PHONE, defValue = "0")
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

    /**
     * 银行卡信息记录
     */
    fun putSelectBankCard(mineBank: MineUserBankList) {
        SpUtils.putString(UserConstant.USER_BANK_SELECT, JsonUtils.toJson(mineBank))
    }

    fun getSelectBankCard(): MineUserBankList? {
        return if (SpUtils.getString(UserConstant.USER_BANK_SELECT) != "") {
            JsonUtils.fromJson(SpUtils.getString(UserConstant.USER_BANK_SELECT).toString(), MineUserBankList::class.java)
        } else null

    }

    /**
     * 是否提示送礼物信息
     */
    fun putSendGiftTips(boolean: Boolean) {
        SpUtils.putBoolean("GiftTips", boolean)
    }

    fun getSendGiftTips(): Boolean {
        return SpUtils.getBoolean("GiftTips", true)
    }

    /**
     * 是否设置登录密码
     */

    fun putIsSetPayPassWord(boolean: Boolean) {
        SpUtils.putBoolean("PayPassWord", boolean)
    }

    fun getIsSetPayPassWord(): Boolean {
        return SpUtils.getBoolean("PayPassWord", false)
    }

}