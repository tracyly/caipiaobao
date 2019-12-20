package com.fenghuang.caipiaobao.ui.mine.password

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.widget.dialog.SuccessDialog
import com.fenghuang.caipiaobao.widget.timer.CountDownTimerUtils
import kotlinx.android.synthetic.main.fregamnt_modify_password_phone_next.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/12- 14:20
 * @ Describe
 *
 */

class MineModifyPasswordByPhoneNext : BaseNavFragment() {


    override fun getPageTitle() = "密码修改"

    override fun isOverridePage() = false

    override fun getContentResID() = R.layout.fregamnt_modify_password_phone_next

    override fun isShowBackIcon() = true

    override fun isShowBackIconWhite() = true

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun initEvent() {


        btModify.setOnClickListener {

            if (TextUtils.isEmpty(etPhoneNewPass11.text.toString())) {
                ToastUtils.showWarning("请输入新密码")
                return@setOnClickListener
            }
            if (etPhoneNewPass11.text.length < 6) {
                ToastUtils.showWarning("密码长度最少为6位")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(etNewPass11Sure.text.toString())) {
                ToastUtils.showWarning("请输入新密码")
                return@setOnClickListener
            }
            if (etNewPass11Sure.text.length < 6) {
                ToastUtils.showWarning("密码长度最少为6位")
                return@setOnClickListener
            }
            if (etNewPass11Sure.text != etPhoneNewPass11.text) {
                ToastUtils.showWarning("两次新密码输入不一致")
                return@setOnClickListener
            }
            showPageLoadingDialog()
            LoginApi.modifyPassWord(arguments?.getString("verfy_phone")!!, arguments?.getString("verfy_code")!!, etPhoneNewPass11.text.toString(), 1) {
                onSuccess {

                    val dialog = SuccessDialog(getPageActivity(), "修改成功", R.mipmap.ic_dialog_success)
                    dialog.setOnDismissListener { pop() }
                    dialog.show()
                    hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showError("修改失败:${it.getMsg()}")
                    hidePageLoadingDialog()
                }
            }


        }
    }


    /**
     * 倒计时
     */

    fun time(textView: TextView) {
        val mCountDownTimerUtils = CountDownTimerUtils(textView, 120000, 1000)
        mCountDownTimerUtils.start()
    }


    companion object {
        fun newInstance(verfy_code: String, verfy_phone: String): MineModifyPasswordByPhoneNext {
            val fragment = MineModifyPasswordByPhoneNext()
            val mBundle = Bundle()
            mBundle.putString("verfy_code", verfy_code)
            mBundle.putString("verfy_phone", verfy_phone)
            fragment.arguments = mBundle
            return fragment
        }
    }

}