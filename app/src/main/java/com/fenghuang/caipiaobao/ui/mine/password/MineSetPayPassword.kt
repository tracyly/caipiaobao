package com.fenghuang.caipiaobao.ui.mine.password

import android.text.Editable
import android.text.TextWatcher
import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.mine.data.MineIsSetPayPass
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.SuccessDialog
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.fregamnt_set_pay_password.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/12- 14:20
 * @ Describe
 *
 */

class MineSetPayPassword : BaseNavFragment() {

    private var oldPass: String = ""

    private var isFirst: Boolean = true

    override fun isShowBackIcon() = true

    override fun isShowBackIconWhite() = false

    override fun getPageTitle() = "支付密码设置"

    override fun getContentResID() = R.layout.fregamnt_set_pay_password

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }


    override fun initEvent() {
        edit_pay_solid.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 6) {
                    if (isFirst) {
                        oldPass = p0.toString()
                        edit_pay_solid.clearText()
                        isFirst = false
                        tvTitlePass.text = "请确认支付密码"
                    } else {
                        if (oldPass == p0.toString()) {
                            veyfyPass()
                        } else tvSetError.text = "两次密码输入不一致"
                    }

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

    }

    fun veyfyPass() {
        showPageLoadingDialog()
        HomeApi.getSettingPayPassword("", edit_pay_solid.text.toString()) {
            onSuccess {
                val dialog = SuccessDialog(getPageActivity(), "支付密码设置成功", R.mipmap.ic_dialog_success)
                dialog.setOnDismissListener { pop() }
                dialog.show()
                UserInfoSp.putIsSetPayPassWord(true)
                RxBus.get().post(MineIsSetPayPass(true))
                hidePageLoadingDialog()
            }
            onFailed {
                hidePageLoadingDialog()
                tvSetError.text = it.getMsg()
            }
        }
    }


}