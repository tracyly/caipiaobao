package com.fenghuang.caipiaobao.ui.login

import android.view.View
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.utils.CheckUtils
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @ Author  QinTian
 * @ Date  2019/10/16- 16:24
 * @ Describe 登录页
 */

class LoginFragment : BaseMvpFragment<LoginPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LoginPresenter()

    override fun isOverridePage() = false

    override fun isShowBackIconWhite() = false

    override fun getPageTitle() = getString(R.string.login_with_identify_code)

    override fun getContentResID() = R.layout.fragment_login

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }


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
                mPresenter.time(tvGetIdentifyCode)
                mPresenter.userGetCode(etIdentifyCode, etPhoneNum.text.toString(), "login")
            } else {
                ToastUtils.showError("请输入正确11位手机号码")
            }
        }

        btLogin.setOnClickListener {
            etIdentifyCode.text
            if (tvLoginType.text == getString(R.string.login_with_password_)) {
                if (etIdentifyCode.text.length < 4) {
                    ToastUtils.showError("请输入正确验证码")
                } else {
                    mPresenter.userLoginWithIdentify(etPhoneNum.text.toString(), etIdentifyCode.text.toString(), 0)
                }
            } else {
                if (etPassWord.text.length < 6) {
                    ToastUtils.showError("密码最少6位")
                } else {
                    mPresenter.userLoginWithPassWord(etPhoneNum.text.toString(), etPassWord.text.toString())
                }
            }
        }

        tvRegister.setOnClickListener {
            start(LoginRegisterFragment())
        }
    }
}