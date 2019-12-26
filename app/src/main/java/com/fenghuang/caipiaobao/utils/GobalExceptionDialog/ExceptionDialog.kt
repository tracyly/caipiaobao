package com.fenghuang.caipiaobao.utils.GobalExceptionDialog


import android.content.Context
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.login.LoginFragment
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.ui.mine.data.MinePassWordTime
import com.fenghuang.caipiaobao.ui.mine.password.MineSetPayPassword
import com.fenghuang.caipiaobao.utils.JsonUtils
import com.fenghuang.caipiaobao.utils.LaunchUtils.startFragment
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.TipsConfirmDialog
import com.hwangjr.rxbus.RxBus
import com.pingerx.rxnetgo.exception.ApiException


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/17- 14:53
 * @ Describe : 公共提示确认对话框
 *
 */


object ExceptionDialog {

    //登录过期
    fun showExpireDialog(context: Context, exception: ApiException) {
        if (exception.getDataCode().toString() == "401" || exception.getCode() == 2002 || exception.getCode() == 2000 || exception.getCode() == 2001 || exception.message == "请登录后操作") {
            loginDialog(context)
        } else if (exception.getCode() == 2003) {
            loginMore(context)
        } else if (exception.getCode() == 1002) {
            ToastUtils.showErrorLong("支付密码错误，您还有" + JsonUtils.fromJson(exception.getDataCode().toString(), MinePassWordTime::class.java).remain_times.toString() + "次机会")
        } else if (exception.getCode() == 9) {
            passDialog(context)
        } else {
            ToastUtils.showError(exception.getMsg())
        }
    }

    //登录过期
    fun showExpireDialog(context: Context) {
        if (loginExperDialog == null || !loginExperDialog?.isShowing!!) {
            loginExperDialog = TipsConfirmDialog(context, "未登录或登录已过期", "去登录", "下次再说", "")
            loginExperDialog?.setConfirmClickListener {
                startFragment(context, LoginFragment())
            }
            loginExperDialog?.setCanceledOnTouchOutside(false)
            loginExperDialog?.show()
            RxBus.get().post(LoginSuccess(false, "", -1, "", -1, ""))
        }
        UserInfoSp.putIsLogin(false)
    }

    //不需要提示信息
    fun showExpireDialogNoInfo(context: Context, exception: ApiException) {
        if (exception.getDataCode().toString() == "401" || exception.getCode() == 2002 || exception.getCode() == 2000 || exception.getCode() == 2001 || exception.message == "请登录后操作") {
            val dialog = TipsConfirmDialog(context, "未登录或登录已过期", "去登录", "下次再说", "")
            dialog.setConfirmClickListener {
                startFragment(context, LoginFragment())
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
            UserInfoSp.putIsLogin(false)
        }
    }

    //登陆过期dialog
    private var loginExperDialog: TipsConfirmDialog? = null

    private fun loginDialog(context: Context) {
        loginExperDialog = TipsConfirmDialog(context, "未登录或登录已过期", "去登录", "下次再说", "")
        loginExperDialog?.setConfirmClickListener {
            startFragment(context, LoginFragment())
        }
        loginExperDialog?.setCanceledOnTouchOutside(false)
        loginExperDialog?.show()
        RxBus.get().post(LoginSuccess(false, "", -1, "", -1, ""))
        UserInfoSp.putIsLogin(false)
    }


    //支付密码dialog
    private fun passDialog(context: Context) {
        val dialog = TipsConfirmDialog(context, "您还没设置支付密码", "去设置", "下次再说", "")
        dialog.setConfirmClickListener {
            startFragment(context, MineSetPayPassword())
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    //多端登录dialog
    private var loginMoreDialog: TipsConfirmDialog? = null

    fun loginMore(context: Context) {
        RxBus.get().post(LoginSuccess(false, "", -1, "", -1, ""))
        loginMoreDialog = TipsConfirmDialog(context, "您的账号在其它设备登录", "去登录", "我知道了", "如非本人操作请联系客服")
        loginMoreDialog?.setConfirmClickListener {
            startFragment(context, LoginFragment())
        }
        loginMoreDialog?.setCanceledOnTouchOutside(false)
        loginMoreDialog?.show()
        RxBus.get().post(LoginSuccess(false, "", -1, "", -1, ""))
        UserInfoSp.putIsLogin(false)
    }

    //未设置支付密码
    fun noSetPassWord(context: Context) {
        val dialog = TipsConfirmDialog(context, "您暂未设置支付密码", "去设置", "我知道了", "")
        dialog.setConfirmClickListener {
            startFragment(context, MineSetPayPassword())
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }


    //控制器登录过期
    fun showExpireDialogControll(context: Context, exception: ApiException) {
        if (exception.getDataCode().toString() == "401" || exception.getCode() == 2002 || exception.getCode() == 2000 || exception.getCode() == 2001 || exception.message == "请登录后操作") {
            loginDialog(context)
        } else if (exception.getCode() == 2003) {
            loginMore(context)
        } else if (exception.getCode() == 1002) {
            ToastUtils.showErrorLong("支付密码错误，您还有" + JsonUtils.fromJson(exception.getDataCode().toString(), MinePassWordTime::class.java).remain_times.toString() + "次机会")
        } else if (exception.getCode() == 9) {
            passDialog(context)
        } else {
            ToastUtils.showError(exception.getMsg())
        }
    }
}