package com.fenghuang.caipiaobao.ui.login

import android.view.View
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.login.data.LoginExit
import com.fenghuang.caipiaobao.ui.mine.MineContactCustomerFragment
import com.fenghuang.caipiaobao.utils.CheckUtils
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlinx.android.synthetic.main.fragment_register_success.*

/**
 * @ Author  QinTian
 * @ Date  2019/10/16- 16:24
 * @ Describe 登录页
 */

class LoginFragment : BaseMvpFragment<LoginPresenter>() {

    var identify: String = ""

    var pass = ""
    var phoneNum = ""

    var loginByPhone = false

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LoginPresenter()

    override fun isOverridePage() = false

    override fun isShowBackIconWhite() = false

    override fun isRegisterRxBus() = true

    override fun getPageTitle() = getString(R.string.login_with_password_)

    override fun getContentResID() = R.layout.fragment_login


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)

    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    var registerBtn = false
    override fun initEvent() {
        tvLoginType.setOnClickListener {
            if (tvLoginType.text == getString(R.string.login_with_password_)) {
                tvLoginType.text = getString(R.string.login_with_identify_code)
                rlLoginByIdentify.visibility = View.GONE
                rlLoginByPassWord.visibility = View.VISIBLE
                setPageTitle(getString(R.string.login_with_password))
            } else {
                tvLoginType.text = getString(R.string.login_with_password_)
                rlLoginByIdentify.visibility = View.VISIBLE
                rlLoginByPassWord.visibility = View.GONE
                setPageTitle(getString(R.string.login_with_identify_code))
            }
        }

        tvGetIdentifyCode.setOnClickListener {
            if (CheckUtils.isMobileNumber(etPhoneNum.text.toString())) {
                mPresenter.userGetCode(tvGetIdentifyCode, etPhoneNum.text.toString(), "login")
                loginByPhone = true
            } else {
                ToastUtils.showError("请输入正确11位手机号码")
            }
        }

        btLogin.setOnClickListener {
            etIdentifyCode.text
            if (tvLoginType.text == getString(R.string.login_with_password_)) {
                if (CheckUtils.isMobileNumber(etPhoneNum.text.toString())) {
                    if (loginByPhone) {
                        if (etIdentifyCode.text.toString().length < 4) {
                            ToastUtils.showError("请输入4位验证码")
                        } else {
                            if (identify == etIdentifyCode.text.toString()) {
                                mPresenter.userLoginWithIdentify(etPhoneNum.text.toString(), etIdentifyCode.text.toString(), 0)
                            } else ToastUtils.showError("验证码错误或已过期")
                        }
                    } else ToastUtils.showError("请输获取证码")
                } else ToastUtils.showError("请输入正确11位手机号码")
            } else {
                if (CheckUtils.isMobileNumber(etPhoneNum.text.toString())) {
                    if (etPassWord.text.length < 6) {
                        ToastUtils.showError("密码长度不得小于6位")
                    } else {
                        mPresenter.userLoginWithPassWord(etPhoneNum.text.toString(), etPassWord.text.toString(), false)
                    }
                } else ToastUtils.showError("请输入正确11位手机号码")

            }
        }

        tvRegister.setOnClickListener {
            setVisible(linRegister)
            setGone(linLogin)
            setPageTitle(getString(R.string.login_user_register))
        }

        tvGetCode.setOnClickListener {
            if (CheckUtils.isMobileNumber(tvRegisterPhone.text.toString())) {
                mPresenter.userGetCode(tvGetCode, tvRegisterPhone.text.toString(), "reg")
            } else {
                ToastUtils.showError("请输入正确11位手机号码")
            }
        }


        btRegister.setOnClickListener {
            if (CheckUtils.isMobileNumber(tvRegisterPhone.text.toString())) {
                if (registerBtn) {
                    if (etRegisterCode.text.length >= 4) {
                        if (edRegisterPassWord.text.length >= 6) {
                            mPresenter.userRegister(tvRegisterPhone.text.toString(), etRegisterCode.text.toString(), edRegisterPassWord.text.toString(), "0")
                        } else ToastUtils.showError("密码长度不得小于6位")
                    } else ToastUtils.showError("请输入4位验证码")
                } else ToastUtils.showError("请先获取验证码")
            } else ToastUtils.showError("请输入正确11位手机号码")
        }


        tvGoFuck.setOnClickListener {
            LaunchUtils.startFragment(getPageActivity(), MineContactCustomerFragment())
        }

        tvGoMain.setOnClickListener {
            mPresenter.userLoginWithPassWord(phoneNum, pass, false)
        }

        tvEditInfo.setOnClickListener {
            mPresenter.userLoginWithPassWord(phoneNum, pass, true)
        }

        tvFoGetPass.setOnClickListener {
            LaunchUtils.startFragment(getPageActivity(), LoginForgetPassWord())
        }
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onExit(eventBean: LoginExit) {
        if (eventBean.exit) {
            pop()
        }
    }

    fun setPageTitleLogin(title: String) {
        setPageTitle(title)
    }

}