package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.widget.dialog.TipsConfirmDialog
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/24- 19:32
 * @ Describe 设置
 *
 */

class MineSettingFragment : BaseNavFragment() {


    override fun getContentResID() = R.layout.fragment_setting

    override fun getPageTitle() = getString(R.string.mine_setting)

    override fun isShowBackIconWhite() = false

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initEvent() {
        btExitLogin.setOnClickListener {
            val dialog = TipsConfirmDialog(getPageActivity(), "确认是否退出?", "确认", "取消", "")
            dialog.setConfirmClickListener {
                RxBus.get().post(LoginSuccess(false, "", 0, "", 0))
                this.pop()
            }
            dialog.show()
        }

    }

}