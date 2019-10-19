package com.fenghuang.caipiaobao.ui.login

import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.widget.timer.CountDownTimerUtils

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/18- 15:05
 * @ Describe
 *
 */

class LoginRegisterPresenter : BaseMvpPresenter<LoginRegisterFragment>() {
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
                ToastUtils.showError(it.getMsg())
            }
        }
    }

    /**
     * 注册
     */

    fun userRegister(phone: String, code: String, password: String, is_auto_login: String) {
        LoginApi.userRegister(phone, code, password, is_auto_login) {
            onSuccess {
                ToastUtils.showSuccess("注册成功")
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }
}