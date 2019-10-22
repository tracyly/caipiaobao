package com.fenghuang.caipiaobao.ui.login

import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.widget.timer.CountDownTimerUtils

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/16- 16:25
 * @ Describe
 *
 */

class LoginPresenter : BaseMvpPresenter<LoginFragment>() {


    /**
     * 倒计时
     */

    fun time(textView: TextView) {
        val mCountDownTimerUtils = CountDownTimerUtils(textView, 60000, 1000)
        mCountDownTimerUtils.start()
    }


    /**
     * 获取验证码
     */

    fun userGetCode(editText: EditText, phone: String, type: String) {
        LoginApi.userGetCode(phone, type) {
            onSuccess {
                editText.setText(it.code)
            }
            onFailed {
                ToastUtils.showError(it.toString())
            }
        }
    }


    /**
     * 验证码登录
     */
    fun userLoginWithIdentify(phone: String, code: String, isAutoLogin: Int) {
        LoginApi.userLoginWithIdentify(phone, code, isAutoLogin) {
            onSuccess {
                //                textView.text = it.token
                SpUtils.putString(UserConstant.TOKEN, it.token)
                SpUtils.putInt(UserConstant.USER_ID, it.user_id)
//                ToastUtils.showSuccess("登录成功" + it.token)
                mView.pop()
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }


    /**
     * 密码登录
     */
    fun userLoginWithPassWord(phone: String, passWord: String) {
        LoginApi.userLoginWithPassWord(phone, passWord) {
            onSuccess {
                SpUtils.putString(UserConstant.TOKEN, it.token)
                SpUtils.putInt(UserConstant.USER_ID, it.user_id)
//                ToastUtils.showSuccess("登录成功" + it.tok/en)
                mView.pop()
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }

}