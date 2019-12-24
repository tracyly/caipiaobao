package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.fragment.BaseNavFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.ui.mine.data.MineIsSetPayPass
import com.fenghuang.caipiaobao.ui.mine.password.MineModifyPassword
import com.fenghuang.caipiaobao.ui.mine.password.MineModifyPayPassword
import com.fenghuang.caipiaobao.ui.mine.password.MineSetPayPassword
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.TipsConfirmDialog
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
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

    override fun initData() {
        if (!UserInfoSp.getIsSetPayPassWord()) {
            showPageLoadingDialog()
            HomeApi.isSetPassWord {
                onSuccess {
                    tvPayPassWordSet.text = "支付密码修改"
                    UserInfoSp.putIsSetPayPassWord(true)
                    hidePageLoadingDialog()
                }
                onFailed {
                    tvPayPassWordSet.text = "支付密码设置"
                    UserInfoSp.putIsSetPayPassWord(false)
                    tvPayPassWordNotSet.text = it.getMsg()
                    hidePageLoadingDialog()
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
    }

    override fun initEvent() {
        btExitLogin.setOnClickListener {
            val dialog = TipsConfirmDialog(getPageActivity(), "确认是否退出?", "确认", "取消", "")
            dialog.setConfirmClickListener {
                RxBus.get().post(LoginSuccess(false, "", -1, "", -1, ""))
                this.pop()
            }
            dialog.show()
        }

        setPassWord.setOnClickListener {
            LaunchUtils.startFragment(getPageActivity(), MineModifyPassword())
        }
        linSetPayPassWord.setOnClickListener {
            if (tvPayPassWordNotSet.text.toString().contains("冻结")) return@setOnClickListener
            if (tvPayPassWordNotSet.text == "暂未设置支付密码") {

                LaunchUtils.startFragment(getPageActivity(), MineSetPayPassword())
            } else {

                LaunchUtils.startFragment(getPageActivity(), MineModifyPayPassword())
            }
        }
    }

    /**
     * 是否设置支付密码
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onStartPresonal(eventBean: MineIsSetPayPass) {
        if (eventBean.isSet) tvPayPassWordNotSet.text = ""
        pop()
    }


}