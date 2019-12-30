package com.fenghuang.caipiaobao.ui.mine.password

import android.text.TextUtils
import android.widget.TextView
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.utils.CheckUtils
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.timer.CountDownTimerUtils
import kotlinx.android.synthetic.main.fregamnt_modify_password_phone.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/12- 14:20
 * @ Describe
 *
 */

class MineModifyPasswordByPhone : BaseNavFragment() {

    private var identifyCodeGet = "-1"

    private var isGetSingCode = false

    override fun getPageTitle() = "密码修改"

    override fun isOverridePage() = false

    override fun getContentResID() = R.layout.fregamnt_modify_password_phone

    override fun isShowBackIcon() = true

    override fun isShowBackIconWhite() = false

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun initEvent() {
        tvGetIdentifyCodePass.setOnClickListener {
            if (CheckUtils.isMobileNumber(etPhonePass.text.toString())) {
                if (UserInfoSp.getUserPhone() == etPhonePass.text.toString()) {
                    LoginApi.userGetCode(etPhonePass.text.toString(), "chg_pwd") {
                        onSuccess {
                            time(tvGetIdentifyCodePass)
                            isGetSingCode = true
                        }
                        onFailed { ToastUtils.showError(it.getMsg()) }
                    }
                } else ToastUtils.showError("该手机号与当前用户不匹配")
            } else ToastUtils.showError("请输入正确11位手机号码")

        }

        btNext.setOnClickListener {
            if (!TextUtils.isEmpty(etPhonePass.text)) {
                if (UserInfoSp.getUserPhone() == etPhonePass.text.toString()) {
                    if (isGetSingCode) {
                        if (!TextUtils.isEmpty(etPhoneSignNum.text)) {
                                pop()
                                LaunchUtils.startFragment(getPageActivity(), MineModifyPasswordByPhoneNext.newInstance(etPhoneSignNum.text.toString(), etPhonePass.text.toString()))
                        } else ToastUtils.showError("请输验证码")
                    } else ToastUtils.showError("请先获取验证码")
                } else ToastUtils.showError("请确认手机号是否正确")
            } else ToastUtils.showError("请输手机号")
        }
    }


    /**
     * 倒计时
     */

    fun time(textView: TextView) {
        val mCountDownTimerUtils = CountDownTimerUtils(textView, 120000, 1000)
        mCountDownTimerUtils.start()
    }


}