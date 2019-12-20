package com.fenghuang.caipiaobao.ui.mine.password

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.ui.mine.data.MinePassWordTime
import com.fenghuang.caipiaobao.utils.JsonUtils
import com.fenghuang.caipiaobao.widget.dialog.SuccessDialog
import kotlinx.android.synthetic.main.fregamnt_modify_pay_password.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/12- 14:20
 * @ Describe
 *
 */

class MineModifyPayPassword : BaseNavFragment() {

    var oldPassWord: String = ""

    var newPassWord1: String = ""

    var isLast: Boolean = false

    override fun isShowBackIcon() = true

    override fun isShowBackIconWhite() = false

    override fun getPageTitle() = "支付密码修改"

    override fun getContentResID() = R.layout.fregamnt_modify_pay_password

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }


    override fun initEvent() {
        edit_solid.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 6) {
                    when {
                        tvTitlePass.text == "请输入旧密码" -> {
                            oldPassWord = edit_solid.text.toString()
                            verifyPass()
                        }
                        tvTitlePass.text == "请输入新密码" -> {
                            if (edit_solid.text.toString() != oldPassWord) {
                                newPassWord1 = edit_solid.text.toString()
                                edit_solid.clearText()
                                tvTitlePass.text = "请确认新密码"
                                isLast = true
                            } else {
                                edit_solid.clearText()
                                ToastUtils.showInfo("新密码不能与旧密码相同")
                            }
                        }
                        tvTitlePass.text == "请确认新密码" -> {
                            if (newPassWord1 == edit_solid.text.toString()) {
                                setPassWord()
                            } else {
                                edit_solid.clearText()
                                ToastUtils.showInfo("两次密码输入不一致")
                            }

                        }
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })


    }

    //验证旧密码
    @SuppressLint("SetTextI18n")
    fun verifyPass() {
        showPageLoadingDialog()
        LoginApi.modifyPassWord(edit_solid.text.toString()) {
            onSuccess {
                hidePageLoadingDialog()
                edit_solid.clearText()
                tvTitlePass.text = "请输入新密码"
                tvError.text = ""
            }
            onFailed {
                hidePageLoadingDialog()
                if (it.getCode() == 1002) {
                    tvError.text = "”旧支付密码错误，您还有" + JsonUtils.fromJson(it.getDataCode().toString(), MinePassWordTime::class.java).remain_times.toString() + "次机会“。" + "且密码连续5次错误后进入冻结状态，冻结之后2小时内无法修改密码"
                } else tvError.text = it.getMsg()
                edit_solid.clearText()
            }
        }
//        edtOldPass.setPassword("")

    }

    //修改密码
    fun setPassWord() {
        showPageLoadingDialog()
        HomeApi.getSettingPayPassword(oldPassWord, edit_solid.text.toString()) {
            onSuccess {
                hidePageLoadingDialog()
                val dialog = SuccessDialog(getPageActivity(), "支付密码设置成功", R.mipmap.ic_dialog_success)
                dialog.setOnDismissListener { pop() }
                dialog.show()
            }
            onFailed {
                hidePageLoadingDialog()
                tvError.text = it.getMsg()
            }
        }
    }

}