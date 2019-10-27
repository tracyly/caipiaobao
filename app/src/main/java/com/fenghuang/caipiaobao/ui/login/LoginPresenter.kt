package com.fenghuang.caipiaobao.ui.login

import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.timer.CountDownTimerUtils
import com.hwangjr.rxbus.RxBus

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
        mView.showPageLoadingDialog("登录中...")
        LoginApi.userLoginWithIdentify(phone, code, isAutoLogin) {
            onSuccess {
                setUserInfo(it.token, it.user_id, phone,it.password_not_set)
                mView.hidePageLoadingDialog()
                mView.pop()
            }
            onFailed {
                mView.hidePageLoadingDialog()
                ToastUtils.showError(it.getMsg())

            }
        }
    }

    /**
     * 密码登录
     */
    fun userLoginWithPassWord(phone: String, passWord: String) {
        mView.showPageLoadingDialog("登录中...")
        LoginApi.userLoginWithPassWord(phone, passWord) {
            onSuccess {
                setUserInfo(it.token, it.user_id, phone,it.password_not_set)
                mView.hidePageLoadingDialog()
                mView.pop()
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }

    private fun setUserInfo(token: String, userId: Int, phone: String,password_not_set:Int) {
        UserInfoSp.putToken(token)
        UserInfoSp.putUserId(userId)
        UserInfoSp.putUserPhone(phone)
        UserInfoSp.putUserIsSetPassWord(password_not_set)
        RxBus.get().post(LoginSuccess(true,token, userId, phone,password_not_set))
    }
}