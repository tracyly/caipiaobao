package com.fenghuang.caipiaobao.ui.login

import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.utils.CheckUtils
import kotlinx.android.synthetic.main.fragment_login_register.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/18- 15:04
 * @ Describe 注册
 *
 */

class LoginRegisterFragment : BaseMvpFragment<LoginRegisterPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LoginRegisterPresenter()

    override fun isOverridePage() = false

    override fun isShowBackIconWhite() = false

    override fun getPageTitle() = getString(R.string.login_user_register)

    override fun getContentResID() = R.layout.fragment_login_register

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }


    override fun initEvent() {
        tvGetCode.setOnClickListener {
            if (CheckUtils.isMobileNumber(tvRegisterPhone.text.toString())) {
                mPresenter.time(tvGetCode)
                mPresenter.userGetCode(etRegisterCode, tvRegisterPhone.text.toString(), "reg")
            } else {
                ToastUtils.showError("请输入正确11位手机号码")
            }
        }

        btRegister.setOnClickListener {
            if (CheckUtils.isMobileNumber(tvRegisterPhone.text.toString())) {
                if (etRegisterCode.text.length >= 4) {
                    if (edRegisterPassWord.text.length >= 6) {
                        mPresenter.userRegister(tvRegisterPhone.text.toString(), etRegisterCode.text.toString(), edRegisterPassWord.text.toString(), "0")
                    } else {
                        ToastUtils.showError("密码长度不得小于6位")
                    }
                } else {
                    ToastUtils.showError("请输入验证码")
                }
            } else {
                ToastUtils.showError("请输入正确11位手机号码")
            }
        }
    }

}