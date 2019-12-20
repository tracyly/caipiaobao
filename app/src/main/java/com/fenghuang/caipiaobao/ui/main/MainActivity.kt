package com.fenghuang.caipiaobao.ui.main

import android.Manifest
import android.os.Bundle
import com.fenghuang.baselib.base.activity.BasePageActivity
import com.fenghuang.baselib.utils.AppUtils
import com.fenghuang.baselib.utils.DebugUtils
import com.fenghuang.caipiaobao.function.doOnIOThread
import com.fenghuang.caipiaobao.helper.DestroyHelper
import com.fenghuang.caipiaobao.helper.RxPermissionHelper


/**
 * 主页面，用来填充Fragment,和处理相关的Intent
 */
class MainActivity : BasePageActivity() {

    override fun getPageFragment() = MainFragment()

    override fun onBackPressedSupport() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressedSupport()
        } else {
            if (DebugUtils.isDevModel()) {
                super.onBackPressedSupport()
            } else {
                AppUtils.moveTaskToBack(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initPreData()
        super.onCreate(savedInstanceState)
        checkDialog()
    }

    /***
     * 回到主页面弹出一些列的窗口
     */
    private fun checkDialog() {
        // 权限弹窗
        RxPermissionHelper.request(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    }


    /**
     * 退出时取消网络相关的请求
     */
    override fun onDestroy() {
        DestroyHelper.onDestroy()
        super.onDestroy()
    }

    /**
     * 初始化一些数据
     */

    private fun initPreData() {
        doOnIOThread {
        }
//        anchorNotify()
    }

//    private val pushTime: Long = 5000
//    var timer: CountDownTimer? = null
//    fun anchorNotify() {
//        //初始化主播开播提醒
//        timer = object : CountDownTimer(pushTime, 1000) {
//            //根据间隔时间来不断回调此方法，这里是每隔1000ms调用一次
//            override fun onTick(millisUntilFinished: Long) {
//                //todo millisUntilFinished为剩余时间，也就是30000 - n*1000
//
//            }
//
//            //结束倒计时调用
//            override fun onFinish() {
//                anchorPush()
//            }
//        }
//        timer?.start()
//    }
//

//
//    private fun popDown() {
//        val message = Message()
//        message.what = COMPLETED
//        handler.sendMessage(message)
//    }
//
//    //自定义停留时间
//    private fun showMyToast(popup: AnchoePushPopup, cnt: Int) {
//        val timer = Timer()
//        timer.schedule(object : TimerTask() {
//            override fun run() {
//                Looper.prepare()
//                popup.showAtLocation(this@MainActivity.findView(R.id.pageContainer), Gravity.TOP, 0, 0)
//                Looper.loop()
//            }
//        }, 0, 3000)
//        Timer().schedule(object : TimerTask() {
//            override fun run() {
//                popup.dismiss()
//                timer.cancel()
//            }
//
//        }, cnt.toLong())
//
//    }
//
//    private val COMPLETED = 0
//    private val handler = @SuppressLint("HandlerLeak")
//    object : Handler() {
//        override fun handleMessage(msg: Message) {
//            if (msg.what === COMPLETED) {
//                val popup = AnchoePushPopup(this@MainActivity)
//                popup.width = ViewUtils.dp2px(280)
//                popup.height = ViewUtils.dp2px(40)
//                popup.animationStyle = R.style.popAnim
//                showMyToast(popup, 2000)
//            }
//        }
//    }
}
