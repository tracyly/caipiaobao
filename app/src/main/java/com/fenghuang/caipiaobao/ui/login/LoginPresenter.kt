package com.fenghuang.caipiaobao.ui.login

import android.widget.TextView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.ui.login.data.LoginExitStart
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.timer.CountDownTimerUtils
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject

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
        val mCountDownTimerUtils = CountDownTimerUtils(textView, 120000, 1000)
        mCountDownTimerUtils.start()
    }


    /**
     * 获取验证码
     */

    fun userGetCode(editText: TextView, phone: String, type: String) {
        LoginApi.userGetCode(phone, type) {
            onSuccess {
                if (mView.isActive()) {
                    mView.identify = it.code
                    time(editText)
                    mView.registerBtn = true
//                editText.setText(it.code)
                }
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
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
                if (mView.isActive()) {
                    setUserInfo(it.token, it.user_id, phone, it.password_not_set, it.avatar, it.user_type)
                    userIsFirstRecharge(it.user_id, it.token)
                }
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
    fun userLoginWithPassWord(phone: String, passWord: String, boolean: Boolean) {
        mView.showPageLoadingDialog("登录中...")
        LoginApi.userLoginWithPassWord(phone, passWord) {
            onSuccess {
                if (mView.isActive()) {
                    setUserInfo(it.token, it.user_id, phone, it.password_not_set, it.avatar, it.user_type)
                    userIsFirstRecharge(it.user_id, it.token)
                    if (!boolean) {
                        mView.pop()
                    } else {
                        RxBus.get().post(LoginExitStart(true))
                        mView.pop()
                    }
                }
                mView.hidePageLoadingDialog()
            }
            onFailed {
                ToastUtils.showError(it.getMsg())
                mView.hidePageLoadingDialog()
            }
        }
    }

    /**
     * 是否首冲
     */
    private fun userIsFirstRecharge(user_id: Int, token: String) {
        LoginApi.isFirstRecharge(user_id, token) {
            onSuccess {
                if (mView.isActive()) {
                    if (JSONObject(it).optString("isfirst") == "0") {
                        UserInfoSp.putIsFirstRecharge(false)
                    } else UserInfoSp.putIsFirstRecharge(true)
                    mView.hidePageLoadingDialog()
                    mView.pop()
                }
            }
            onFailed {
                mView.hidePageLoadingDialog()
                mView.pop()
            }
        }
    }

    private fun setUserInfo(token: String, userId: Int, phone: String, password_not_set: Int, avatar: String, userType: String) {
        UserInfoSp.putToken(token)
        UserInfoSp.putUserId(userId)
        UserInfoSp.putUserPhoto(avatar)
        UserInfoSp.putUserPhone(phone)
        UserInfoSp.putUserIsSetPassWord(password_not_set)
        UserInfoSp.setUserType(userType)
        RxBus.get().post(LoginSuccess(true, token, userId, phone, password_not_set, avatar))
    }


    //---------------------------------

    /**
     * 注册
     */
    fun userRegister(phone: String, code: String, password: String, is_auto_login: String) {
        mView.showPageLoadingDialog()
        LoginApi.userRegister(phone, code, password, is_auto_login) {
            onSuccess {
                if (mView.isActive()) {
                    mView.hidePageLoadingDialog()
                    SpUtils.putString(UserConstant.USER_PHONE, phone)
                    mView.setVisible(mView.linLoginSuccess)
                    mView.setPageTitleLogin("注册成功")
                    mView.setGone(mView.linLoginLayout)
                    mView.phoneNum = phone
                    mView.pass = password
//                    userLoginWithPassWord(phone, password)
//                    LaunchUtils.startFragment(mView.requireActivity(), LoginRegisterSuccessFragment.newInstance(phone, password))
//                    val dialog = SuccessDialog(mView.requireContext(), "注册成功！", R.mipmap.ic_dialog_success)
//                    dialog.show()
//                    val t = Timer()
//                    t.schedule(object : TimerTask() {
//                        override fun run() {
//                            dialog.dismiss()
//                            mView.pop()
//                        }
//                    }, 1500)
                }
            }
            onFailed {
                mView.hidePageLoadingDialog()
                ToastUtils.showError(it.getMsg())
            }
        }
    }
}