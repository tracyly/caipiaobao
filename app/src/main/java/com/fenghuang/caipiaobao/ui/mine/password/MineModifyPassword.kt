package com.fenghuang.caipiaobao.ui.mine.password

import android.text.TextUtils
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.login.LoginFragment
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.ui.mine.data.MinePassWordTime
import com.fenghuang.caipiaobao.utils.FastClickUtils
import com.fenghuang.caipiaobao.utils.JsonUtils
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.widget.dialog.SuccessDialog
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.fregamnt_modify_password.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/12- 14:20
 * @ Describe
 *
 */

class MineModifyPassword : BaseNavFragment() {


    override fun getPageTitle() = "密码修改"

    override fun isOverridePage() = false

    override fun getContentResID() = R.layout.fregamnt_modify_password

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
        btModify.setOnClickListener {
            if (FastClickUtils.isFastClick()) {

                if (TextUtils.isEmpty(tvNewPassOld.text.toString())) {
                    ToastUtils.showWarning("请填写旧密码")
                    return@setOnClickListener
                }
                if (tvNewPassOld.text.length < 6) {
                    ToastUtils.showWarning("密码长度最少为6位")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(tvNewPass1.text.toString())) {
                    ToastUtils.showWarning("请填写新密码")
                    return@setOnClickListener
                }
                if (tvNewPass1.text.length < 6) {
                    ToastUtils.showWarning("密码长度最少为6位")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(tvNewPass2.text.toString())) {
                    ToastUtils.showWarning("请确认新密码")
                    return@setOnClickListener
                }
                if (tvNewPass2.text.length < 6) {
                    ToastUtils.showWarning("密码长度最少为6位")
                    return@setOnClickListener
                }
                if (tvNewPassOld.text.toString() == tvNewPass2.text.toString() || tvNewPassOld.text.toString() == tvNewPass1.text.toString()) {
                    ToastUtils.showWarning("新密码与旧密码不能相同")
                    return@setOnClickListener
                }
                if (tvNewPass1.text.toString() == tvNewPass2.text.toString()) {
                    showPageLoading()
                    MineApi.modifyPassWord(tvNewPassOld.text.toString(), tvNewPass1.text.toString()) {
                        onSuccess {
                            hidePageLoading()
                            val dialog = SuccessDialog(getPageActivity(), "修改成功", R.mipmap.ic_dialog_success)
                            dialog.setOnDismissListener {
                                RxBus.get().post(LoginSuccess(false, "", -1, "", -1, ""))
                                pop()
                                LaunchUtils.startFragment(context, LoginFragment())
                            }
                            dialog.show()
                        }
                        onFailed {
                            if (it.getCode() == 1002) {
                                ToastUtils.showError(it.getMsg().toString() + "," + "您还有" + JsonUtils.fromJson(it.getDataCode().toString(), MinePassWordTime::class.java).remain_times.toString() + "次机会")
                            } else ToastUtils.showError(it.getMsg())
                            hidePageLoading()
                        }
                    }
                } else ToastUtils.showNormal("两次新密码输入不一致")
            }
        }
        modifyByPhone.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                pop()
                LaunchUtils.startFragment(getPageActivity(), MineModifyPasswordByPhone())
            }
        }
    }

}