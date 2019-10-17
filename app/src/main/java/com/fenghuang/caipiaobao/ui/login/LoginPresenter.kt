package com.fenghuang.caipiaobao.ui.login

import android.widget.TextView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.caipiaobao.ui.login.data.LoginApi

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/16- 16:25
 * @ Describe
 *
 */

class LoginPresenter : BaseMvpPresenter<LoginFragment>() {

    fun userGetCode(textView: TextView, phone: String) {
        LoginApi.userGetCode(phone) {
            onSuccess {
                textView.text = it.code
            }

            onFailed {
                textView.text = it.toString()
            }
        }
    }

    fun userRegister(textView: TextView, phone: String, code: String) {
        LoginApi.userRegister(phone, code) {
            onSuccess {
                textView.text = it.toString()
            }

            onFailed {
                textView.text = it.toString()
            }

        }
    }

    fun useLogin(textView: TextView, phone: String, code: String) {
        LoginApi.userLogin(phone, code) {
            onSuccess {
                textView.text = it.token
                SpUtils.putString("token", it.token)
                SpUtils.putInt("user_id", it.user_id)
            }
            onFailed {
                textView.text = it.toString()
            }
        }
    }
}