import android.content.Context
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.login.LoginFragment
import com.fenghuang.caipiaobao.utils.LaunchUtils.startFragment
import com.fenghuang.caipiaobao.widget.dialog.TipsConfirmDialog
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
        LogUtils.e("--->"+exception.getMsg()+"-+++-"+exception.message+"---")
        if (exception.getDataCode().toString() == "401" || exception.getCode() == 2002 || exception.getCode() == 2000 || exception.getCode() == 2001) {
            val dialog = TipsConfirmDialog(context, "未登录或登录已过期", "去登录", "下次再说","")
            dialog.setConfirmClickListener {
                startFragment(context, LoginFragment())
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        } else {
            ToastUtils.showError(exception.getMsg())
            LogUtils.e("--->"+exception.getMsg()+"--"+exception.message+"---")
        }
    }
}