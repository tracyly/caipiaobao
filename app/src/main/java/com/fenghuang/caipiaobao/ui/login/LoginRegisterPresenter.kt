package com.fenghuang.caipiaobao.ui.login

import android.widget.EditText
import android.widget.TextView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.ui.login.data.LoginRegisterSuccess
import com.fenghuang.caipiaobao.widget.dialog.SuccessDialog
import com.fenghuang.caipiaobao.widget.timer.CountDownTimerUtils
import com.hwangjr.rxbus.RxBus
import java.util.*

/**
 * @ Author  QinTian
 * @ Date  2019/10/18- 15:05
 * @ Describe
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
        mView.showPageLoadingDialog()
        LoginApi.userRegister(phone, code, password, is_auto_login) {
            onSuccess {
                mView.hidePageLoadingDialog()
                RxBus.get().post(LoginRegisterSuccess(phone))
                SpUtils.putString(UserConstant.USER_PHONE, phone)
                val dialog = SuccessDialog(mView.requireContext(), "注册成功！", R.mipmap.ic_dialog_success)
                dialog.show()
                val t = Timer()
                t.schedule(object : TimerTask() {
                    override fun run() {
                        dialog.dismiss()
                        mView.pop()
                    }
                }, 1500)
            }
            onFailed {
                mView.hidePageLoadingDialog()
                ToastUtils.showError(it.getMsg())
            }
        }
    }
}