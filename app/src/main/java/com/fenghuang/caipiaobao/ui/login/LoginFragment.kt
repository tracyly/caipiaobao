package com.fenghuang.caipiaobao.ui.login

import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.caipiaobao.R
import kotlinx.android.synthetic.main.fragment_login.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/16- 16:24
 * @ Describe 登录页
 *
 */

class LoginFragment : BaseMvpFragment<LoginPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LoginPresenter()

    override fun isOverridePage() = false

    override fun isShowBackIconWhite() = false

    override fun getContentResID() = R.layout.fragment_login

    override fun initEvent() {
        btCode.setOnClickListener {
            mPresenter.userGetCode(tvTextShow1, etPhone.text.toString())
        }

        btRegist.setOnClickListener {
            mPresenter.userRegister(tvTextShow, etPhone.text.toString(), tvTextShow1.text.toString())
        }
        btLogin.setOnClickListener {
            mPresenter.useLogin(tvTextShow2, etPhone.text.toString(), tvTextShow1.text.toString())
        }
    }
}