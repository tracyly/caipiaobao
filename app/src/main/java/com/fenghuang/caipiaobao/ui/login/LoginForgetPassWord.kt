package com.fenghuang.caipiaobao.ui.login

import android.widget.TextView
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.ui.mine.MineContactCustomerFragment
import com.fenghuang.caipiaobao.utils.CheckUtils
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.SuccessDialog
import com.fenghuang.caipiaobao.widget.timer.CountDownTimerUtils
import kotlinx.android.synthetic.main.fragment_forget_password.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/17- 17:39
 * @ Describe
 *
 */
class LoginForgetPassWord : BaseNavFragment() {


    var identify = ""

    override fun isOverridePage() = false

    override fun isShowBackIconWhite() = false

    override fun isRegisterRxBus() = true

    override fun getPageTitle() = "忘记密码"


    override fun getContentResID() = R.layout.fragment_forget_password


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)

    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }


    override fun initEvent() {

        tvGoFuckContent.setOnClickListener {
            LaunchUtils.startFragment(getPageActivity(), MineContactCustomerFragment())
        }
        tvGoFuckContent2.setOnClickListener {
            LaunchUtils.startFragment(getPageActivity(), MineContactCustomerFragment())
        }

        btResetPassWord.setOnClickListener {
            if (CheckUtils.isMobileNumber(etForgetPhoneNum.text.toString())) {
                if (etForgetIdentifyCode.text.toString() == identify) {
                    setGone(linReset1)
                    setVisible(linReset2)
                    tvForgetPhoneNum.text = etForgetPhoneNum.text.toString()
                } else ToastUtils.showError("验证码错误")
//                mPresenter.time(tvGetIdentifyCode)
//                mPresenter.userGetCode(etIdentifyCode, etPhoneNum.text.toString(), "login")
            } else ToastUtils.showError("请输入正确11位手机号码")
        }

        tvForgetGetIdentifyCode.setOnClickListener {
            if (CheckUtils.isMobileNumber(etForgetPhoneNum.text.toString())) {
                if (etForgetPhoneNum.text.toString() == UserInfoSp.getUserPhone()) {
                    userGetCode(etForgetPhoneNum.text.toString(), "retrieve_pwd")
                } else ToastUtils.showError("请确认手机号码是否正确")
            } else ToastUtils.showError("请输入正确11位手机号码")

        }

        btResetPassWord2.setOnClickListener {
            if (etPassWordReset1.text.length < 6) {
                ToastUtils.showError("密码能少于6位")
                return@setOnClickListener
            }
            if (etPassWordReset2.text.length < 6) {
                ToastUtils.showError("密码能少于6位")
                return@setOnClickListener
            }
            if (etPassWordReset1.text.toString() == etPassWordReset2.text.toString()) {
                modifyPass(tvForgetPhoneNum.text.toString(), identify, etPassWordReset2.text.toString(), "1")
            } else ToastUtils.showError("两次密码不一致")
        }
    }


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

    private fun userGetCode(phone: String, type: String) {
        showPageLoadingDialog()
        LoginApi.userGetCode(phone, type) {
            onSuccess {
                hidePageLoadingDialog()
                time(tvForgetGetIdentifyCode)
                identify = it.code
            }
            onFailed {
                hidePageLoadingDialog()
                ToastUtils.showError(it.getMsg())
            }
        }
    }

    /**
     * 修改密码
     */
    private fun modifyPass(phone: String, captcha: String, new_pwd: String, type: String) {
        showPageLoadingDialog()
        LoginApi.userGetCodeNoType(phone, captcha, new_pwd, type) {
            onSuccess {
                hidePageLoadingDialog()
                val dialog = SuccessDialog(getPageActivity(), "修改成功", R.mipmap.ic_dialog_success)
                dialog.setOnDismissListener { pop() }
                dialog.show()
            }
            onFailed {
                hidePageLoadingDialog()
                ToastUtils.showError(it.getMsg())
            }
        }
    }
}